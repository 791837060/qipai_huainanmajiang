package com.anbang.qipai.admin.msg.receiver.resultreceiver;

import com.anbang.qipai.admin.msg.channel.sink.resultsink.YizhengMajiangResultSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.games.GameRoom;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.GamePanPlayerResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.majiang.YizhengMajiangJuPlayerResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.majiang.YizhengMajiangPanPlayerResult;
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

@EnableBinding(YizhengMajiangResultSink.class)
public class YizhengMajiangResultMsgReceiver {
    @Autowired
    private GameService gameService;

    @Autowired
    private GameHistoricalJuResultService gameHistoricalJuResultService;

    @Autowired
    private GameHistoricalPanResultService gameHistoricalPanResultService;

    private Gson gson = new Gson();

    @StreamListener(YizhengMajiangResultSink.YIZHENGMAJIANGRESULT)
    public void recordMajiangHistoricalResult(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        // 游戏局盘记录存储
        Map map = gson.fromJson(json, Map.class);
        if ("yizhengMajiang ju result".equals(msg)) {
            Object gid = map.get("gameId");
            Object dyjId = map.get("dayingjiaId");
            Object dthId = map.get("datuhaoId");
            if (gid != null && dyjId != null && dthId != null) {
                String gameId = (String) gid;
                GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.yizhengMajiang, gameId);

                GameHistoricalJuResult majiangHistoricalResult = new GameHistoricalJuResult();
                majiangHistoricalResult.setGameId(gameId);
                if (room != null) {
                    majiangHistoricalResult.setRoomNo(room.getNo());
                }
                majiangHistoricalResult.setGame(Game.yizhengMajiang);
                majiangHistoricalResult.setDayingjiaId((String) dyjId);
                majiangHistoricalResult.setDatuhaoId((String) dthId);

                Object playerList = map.get("playerResultList");
                if (playerList != null) {
                    List<GameJuPlayerResult> juPlayerResultList = new ArrayList<>();
                    ((List) playerList).forEach((juPlayerResult) -> juPlayerResultList.add(new YizhengMajiangJuPlayerResult((Map) juPlayerResult)));
                    majiangHistoricalResult.setPlayerResultList(juPlayerResultList);

                    majiangHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
                    majiangHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
                    majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

                    gameHistoricalJuResultService.addGameHistoricalResult(majiangHistoricalResult);
                }
            }
        }
        if ("yizhengMajiang pan result".equals(msg)) {
            Object gid = map.get("gameId");
            if (gid != null) {
                String gameId = (String) gid;
                GameHistoricalPanResult majiangHistoricalResult = new GameHistoricalPanResult();
                majiangHistoricalResult.setGameId(gameId);
                majiangHistoricalResult.setGame(Game.yizhengMajiang);

                Object playerList = map.get("playerResultList");
                if (playerList != null) {
                    List<GamePanPlayerResult> panPlayerResultList = new ArrayList<>();
                    ((List) map.get("playerResultList")).forEach((panPlayerResult) -> panPlayerResultList.add(new YizhengMajiangPanPlayerResult((Map) panPlayerResult)));
                    majiangHistoricalResult.setPlayerResultList(panPlayerResultList);

                    majiangHistoricalResult.setNo(((Double) map.get("no")).intValue());
                    majiangHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

                    gameHistoricalPanResultService.addGameHistoricalResult(majiangHistoricalResult);
                }
            }
        }
    }
}
