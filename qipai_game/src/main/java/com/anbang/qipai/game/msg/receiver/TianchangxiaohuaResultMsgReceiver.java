package com.anbang.qipai.game.msg.receiver;

import com.anbang.qipai.game.msg.channel.sink.TianchangxiaohuaResultSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GamePanPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.majiang.TianchangxiaohuaJuPlayerResult;
import com.anbang.qipai.game.plan.bean.historicalresult.majiang.TianchangxiaohuaPanPlayerResult;
import com.anbang.qipai.game.plan.service.GameHistoricalJuResultService;
import com.anbang.qipai.game.plan.service.GameHistoricalPanResultService;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.MemberLatAndLonService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableBinding(TianchangxiaohuaResultSink.class)
public class TianchangxiaohuaResultMsgReceiver {
    @Autowired
    private GameHistoricalJuResultService majiangHistoricalResultService;

    @Autowired
    private GameHistoricalPanResultService majiangHistoricalPanResultService;

    @Autowired
    private MemberLatAndLonService memberLatAndLonService;

    @Autowired
    private GameService gameService;

    private Gson gson = new Gson();

    @StreamListener(TianchangxiaohuaResultSink.TIANCHANGXIAOHUARESULT)
    public void recordMajiangHistoricalResult(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        Map map = gson.fromJson(json, Map.class);
        if ("tianchangxiaohua ju result".equals(msg)) {
            Object gid = map.get("gameId");
            Object dyjId = map.get("dayingjiaId");
            Object dthId = map.get("datuhaoId");
            if (gid != null && dyjId != null && dthId != null) {
                String gameId = (String) gid;
                GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.tianchangxiaohua, gameId);
                if (room != null) {
                    GameHistoricalJuResult majiangHistoricalResult = new GameHistoricalJuResult();
                    majiangHistoricalResult.setGameId(gameId);
                    majiangHistoricalResult.setRoomNo(room.getNo());
                    majiangHistoricalResult.setGame(Game.tianchangxiaohua);
                    majiangHistoricalResult.setDayingjiaId((String) dyjId);
                    majiangHistoricalResult.setDatuhaoId((String) dthId);
                    Object playerList = map.get("playerResultList");
                    if (playerList != null) {
                        List<GameJuPlayerResult> juPlayerResultList = new ArrayList<>();
                        ((List) map.get("playerResultList")).forEach((juPlayerResult) -> juPlayerResultList.add(new TianchangxiaohuaJuPlayerResult((Map) juPlayerResult)));
                        majiangHistoricalResult.setPlayerResultList(juPlayerResultList);
                        majiangHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
                        majiangHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
                        majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());
                        memberLatAndLonService.tianchangxiaohuaDels(juPlayerResultList);
                        majiangHistoricalResultService.addGameHistoricalResult(majiangHistoricalResult);
                    }
                }
            }
        }
        if ("tianchangxiaohua pan result".equals(msg)) {
            Object gid = map.get("gameId");
            if (gid != null) {
                String gameId = (String) gid;
                GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.tianchangxiaohua, gameId);
                if (room != null) {
                    GameHistoricalPanResult majiangHistoricalResult = new GameHistoricalPanResult();
                    majiangHistoricalResult.setGameId(gameId);
                    majiangHistoricalResult.setGame(Game.tianchangxiaohua);
                    Object playerList = map.get("playerResultList");
                    if (playerList != null) {
                        List<GamePanPlayerResult> panPlayerResultList = new ArrayList<>();
                        ((List) playerList).forEach((panPlayerResult) -> panPlayerResultList.add(new TianchangxiaohuaPanPlayerResult((Map) panPlayerResult)));
                        majiangHistoricalResult.setPlayerResultList(panPlayerResultList);
                        majiangHistoricalResult.setNo(((Double) map.get("no")).intValue());
                        majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());
                        majiangHistoricalPanResultService.addGameHistoricalResult(majiangHistoricalResult);
                    }
                }
            }
        }
    }
}
