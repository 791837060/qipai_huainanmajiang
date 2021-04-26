package com.anbang.qipai.admin.msg.receiver.resultreceiver;

import com.anbang.qipai.admin.msg.channel.sink.resultsink.BijiResultSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.games.GameRoom;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.GamePanPlayerResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.puke.BijiJuPlayerResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.puke.BijiPanPlayerResult;
import com.anbang.qipai.admin.plan.service.gameservice.GameHistoricalJuResultService;
import com.anbang.qipai.admin.plan.service.gameservice.GameHistoricalPanResultService;
import com.anbang.qipai.admin.plan.service.gameservice.GameService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableBinding(BijiResultSink.class)
public class BijiResultMsgReceiver {

    @Autowired
    private GameHistoricalJuResultService gameHistoricalResultService;

    @Autowired
    private GameHistoricalPanResultService gameHistoricalPanResultService;

    @Autowired
    private GameService gameService;

    private Gson gson = new Gson();

    @StreamListener(BijiResultSink.BIJIRESULT)
    public void recordMajiangHistoricalResult(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        Map map = gson.fromJson(json, Map.class);
        if ("biji ju result".equals(msg)) {
            Object gid = map.get("gameId");
            Object dyjId = map.get("dayingjiaId");
            Object dthId = map.get("datuhaoId");
            if (gid != null && dyjId != null && dthId != null) {
                String gameId = (String) gid;
                GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.biji, gameId);

                GameHistoricalJuResult majiangHistoricalResult = new GameHistoricalJuResult();
                majiangHistoricalResult.setGameId(gameId);
                if (room != null) {
                    majiangHistoricalResult.setRoomNo(room.getNo());
                }
                majiangHistoricalResult.setGame(Game.biji);
                majiangHistoricalResult.setDayingjiaId((String) dyjId);
                majiangHistoricalResult.setDatuhaoId((String) dthId);

                Object playerList = map.get("playerResultList");
                if (playerList != null) {
                    List<GameJuPlayerResult> juPlayerResultList = new ArrayList<>();
                    ((List) playerList).forEach((juPlayerResult) -> juPlayerResultList.add(new BijiJuPlayerResult((Map) juPlayerResult)));
                    majiangHistoricalResult.setPlayerResultList(juPlayerResultList);
                    majiangHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
                    majiangHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
                    majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());
                    gameHistoricalResultService.addGameHistoricalResult(majiangHistoricalResult);
                }
            }
        }
        if ("biji pan result".equals(msg)) {
            Object gid = map.get("gameId");
            if (gid != null) {
                String gameId = (String) gid;
                GameHistoricalPanResult majiangHistoricalResult = new GameHistoricalPanResult();
                majiangHistoricalResult.setGameId(gameId);
                majiangHistoricalResult.setGame(Game.biji);
                Object playerList = map.get("playerResultList");
                if (playerList != null) {
                    List<GamePanPlayerResult> panPlayerResultList = new ArrayList<>();
                    ((List) map.get("playerResultList")).forEach((panPlayerResult) -> panPlayerResultList.add(new BijiPanPlayerResult((Map) panPlayerResult)));
                    majiangHistoricalResult.setPlayerResultList(panPlayerResultList);

                    majiangHistoricalResult.setNo(((Double) map.get("no")).intValue());
                    majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

                    gameHistoricalPanResultService.addGameHistoricalResult(majiangHistoricalResult);
                }
            }
        }
    }

}
