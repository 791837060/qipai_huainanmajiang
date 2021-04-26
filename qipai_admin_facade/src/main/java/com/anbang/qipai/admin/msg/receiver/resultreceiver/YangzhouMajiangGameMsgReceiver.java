package com.anbang.qipai.admin.msg.receiver.resultreceiver;

import com.anbang.qipai.admin.msg.channel.sink.resultsink.YangzhouMajiangGameSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.games.GameRoom;
import com.anbang.qipai.admin.plan.bean.games.PlayersRecord;
import com.anbang.qipai.admin.plan.service.gameservice.GameService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.List;
import java.util.Map;

@EnableBinding(YangzhouMajiangGameSink.class)
public class YangzhouMajiangGameMsgReceiver {

    @Autowired
    private GameService gameService;

    private Gson gson = new Gson();

    @StreamListener(YangzhouMajiangGameSink.YANGZHOUMAJIANGGAME)
    public void receive(CommonMO mo) {
        String msg = mo.getMsg();
        if ("playerQuit".equals(msg)) {// 有人退出游戏
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            String playerId = (String) data.get("playerId");
            GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.yangzhouMajiang, gameId);
            if (room != null) {
                List<PlayersRecord> playersRecord = room.getPlayersRecord();
                if (room.isVip()) {
                    for (int i = 0; i < playersRecord.size(); i++) {
                        if (playersRecord.get(i).getPlayerId().equals(playerId)) {
                            // 删除玩家记录
                            playersRecord.remove(i);
                        }
                    }
                    gameService.saveGameRoom(room);
                }
            }
        }
        if ("ju canceled".equals(msg)) {// 一局游戏取消
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            gameService.gameRoomFinished(Game.yangzhouMajiang, gameId);
        }
        if ("ju finished".equals(msg)) {// 一局游戏结束
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            gameService.gameRoomFinished(Game.yangzhouMajiang, gameId);
        }
        if ("pan finished".equals(msg)) {// 一盘游戏结束
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            int no = (int) data.get("no");
            List playerIds = (List) data.get("playerIds");
            gameService.panFinished(Game.yangzhouMajiang, gameId, no, playerIds);
        }
    }
}
