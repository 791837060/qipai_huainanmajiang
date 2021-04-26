package com.anbang.qipai.game.msg.receiver;

import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;
import com.anbang.qipai.game.msg.channel.sink.GaoyouMajiangGameSink;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.games.GameRoom;
import com.anbang.qipai.game.plan.bean.games.PlayersRecord;
import com.anbang.qipai.game.plan.service.GameService;
import com.anbang.qipai.game.plan.service.MemberLatAndLonService;
import com.anbang.qipai.game.remote.service.QipaiMembersRemoteService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.List;
import java.util.Map;

@EnableBinding(GaoyouMajiangGameSink.class)
public class GaoyouMajiangGameMsgReveiver {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRoomCmdService gameRoomCmdService;

    @Autowired
    private QipaiMembersRemoteService qipaiMembersRomoteService;

    @Autowired
    private MemberLatAndLonService memberLatAndLonService;

    private Gson gson = new Gson();

    @StreamListener(GaoyouMajiangGameSink.GAOYOUMAJIANGGAME)
    public void receive(CommonMO mo) {
        String msg = mo.getMsg();
        if ("playerQuit".equals(msg)) {// 有人退出游戏
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            String playerId = (String) data.get("playerId");
            GameRoom room = gameService.findRoomByGameAndServerGameGameId(Game.gaoyouMajiang, gameId);
            if (room != null) {
                List<PlayersRecord> playersRecord = room.getPlayersRecord();
                for (int i = 0; i < playersRecord.size(); i++) {
                    if (playersRecord.get(i).getPlayerId().equals(playerId)) {
                        // 退出玩家花费的玉石
                        int amount = playersRecord.get(i).getPayGold();
                        qipaiMembersRomoteService.gold_givegoldtomember(playerId, amount, "back gold to leave game");
                        // 删除玩家记录
                        playersRecord.remove(i);
                    }
                }
                gameService.saveGameRoom(room);
                memberLatAndLonService.del(playerId);
                gameService.gaoyouMajiangPlayerQuitQame(gameId, playerId);
            }
        }
        if ("ju canceled".equals(msg)) {// 取消游戏
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameRoom gameRoom = gameService.findRoomByGameAndServerGameGameId(Game.gaoyouMajiang, gameId);
            if (gameRoom != null) {
                gameRoomCmdService.removeRoom(gameRoom.getNo());
                List<PlayersRecord> playersRecord = gameRoom.getPlayersRecord();
                // 一盘没有打完，返回玉石
                for (int i = 0; i < playersRecord.size(); i++) {
                    if (gameRoom.getCurrentPanNum() == 0 ) {
                        int amount = playersRecord.get(i).getPayGold();
                        qipaiMembersRomoteService.gold_givegoldtomember(playersRecord.get(i).getPlayerId(), amount, "back gold to leave game");
                        memberLatAndLonService.del(playersRecord.get(i).getPlayerId());
                    }
                    gameService.saveGameRoom(gameRoom);
                }
                gameService.gameRoomFinished(Game.gaoyouMajiang, gameId);
            }
        }
        if ("ju finished".equals(msg)) {// 一局游戏结束
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameRoom gameRoom = gameService.findRoomByGameAndServerGameGameId(Game.gaoyouMajiang, gameId);
            if (gameRoom != null) {
                gameRoomCmdService.removeRoom(gameRoom.getNo());
                List<PlayersRecord> playersRecord = gameRoom.getPlayersRecord();
                // 一盘没有打完，返回玉石
                for (int i = 0; i < playersRecord.size(); i++) {
                    if (gameRoom.getCurrentPanNum() == 0 ) {
                        int amount = playersRecord.get(i).getPayGold();
                        qipaiMembersRomoteService.gold_givegoldtomember(playersRecord.get(i).getPlayerId(), amount, "back gold to leave game");
                        memberLatAndLonService.del(playersRecord.get(i).getPlayerId());
                    }
                    gameService.saveGameRoom(gameRoom);
                }
                gameService.gameRoomFinished(Game.gaoyouMajiang, gameId);
            }
        }
        if ("pan finished".equals(msg)) {// 一盘游戏结束
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            int no = (int) data.get("no");
            List playerIds = (List) data.get("playerIds");
            gameService.panFinished(Game.gaoyouMajiang, gameId, no, playerIds);
        }
        if ("game delay".equals(msg)) {// 游戏延时
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameRoom gameRoom = gameService.findRoomByGameAndServerGameGameId(Game.gaoyouMajiang, gameId);
            if (gameRoom != null) {
                // 延时11小时
                gameService.delayGameRoom(Game.gaoyouMajiang, gameId, gameRoom.getDeadlineTime() + 11 * 60 * 60 * 1000);
            }
        }
    }

}
