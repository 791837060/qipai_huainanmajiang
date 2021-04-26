package com.anbang.qipai.dalianmeng.msg.receiver;

import com.anbang.qipai.dalianmeng.cqrs.c.service.GameTableCmdService;
import com.anbang.qipai.dalianmeng.msg.msjobs.CommonMO;
import com.anbang.qipai.dalianmeng.msg.sink.YangzhouMajiangGameSink;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameTable;
import com.anbang.qipai.dalianmeng.plan.service.MemberLatAndLonService;
import com.anbang.qipai.dalianmeng.plan.service.PlayService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.List;
import java.util.Map;

@EnableBinding(YangzhouMajiangGameSink.class)
public class YangzhouGameMsgReceiver {
    @Autowired
    private PlayService playService;

    @Autowired
    private GameTableCmdService gameTableCmdService;

    @Autowired
    private MemberLatAndLonService memberLatAndLonService;
    private Gson gson = new Gson();

    @StreamListener(YangzhouMajiangGameSink.YANGZHOUMAJIANGGAME)
    public void receive(CommonMO mo) {
        String msg = mo.getMsg();
        if ("playerQuit".equals(msg)) {// 有人退出游戏
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            String playerId = (String) data.get("playerId");
            GameTable table = playService.findTableByGameAndServerGameGameId(Game.yangzhouMajiang, gameId);
            if (table != null) {
                playService.gamePlayerQuitQame(Game.yangzhouMajiang, gameId, playerId);
            }
            memberLatAndLonService.deleteMemberLatAndLon(playerId);
        }
        if ("ju canceled".equals(msg)) {// 取消游戏
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameTable gameTable = playService.findTableByGameAndServerGameGameId(Game.yangzhouMajiang, gameId);

            if (gameTable != null) {
                List<String> players = gameTable.getPlayers();
                if (players!=null){
                    for (String player : players) {
                        memberLatAndLonService.deleteMemberLatAndLon(player);
                    }
                }
                gameTableCmdService.removeTable(gameTable.getNo());
                playService.gameTableFinished(Game.yangzhouMajiang, gameId);
            }
        }

        if ("ju finished".equals(msg)) {// 一局游戏结束
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameTable gameTable = playService.findTableByGameAndServerGameGameId(Game.yangzhouMajiang, gameId);

            if (gameTable != null) {
                List<String> players = gameTable.getPlayers();
                if (players!=null){
                    for (String player : players) {
                        memberLatAndLonService.deleteMemberLatAndLon(player);
                    }
                }
                gameTableCmdService.removeTable(gameTable.getNo());
                playService.gameTableFinished(Game.yangzhouMajiang, gameId);
            }
        }
        if ("pan finished".equals(msg)) {// 一盘游戏结束
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            int no = (int) data.get("no");
            List playerIds = (List) data.get("playerIds");
            GameTable gameTable = playService.findTableByGameAndServerGameGameId(Game.yangzhouMajiang, gameId);
            if (gameTable != null) {
                playService.panFinished(Game.yangzhouMajiang, gameId, no, playerIds);
            }
        }
        if ("game start".equals(msg)) {// 游戏开始
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameTable gameTable = playService.findTableByGameAndServerGameGameId(Game.yangzhouMajiang, gameId);
            if (gameTable != null) {
                playService.gameTablePlaying(Game.yangzhouMajiang, gameId);
            }
        }
        if ("game delay".equals(msg)) {// 游戏延时
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameTable gameTable = playService.findTableByGameAndServerGameGameId(Game.yangzhouMajiang, gameId);
            if (gameTable != null) {
                // 延时1小时
                playService.delayGameTable(Game.yangzhouMajiang, gameId, gameTable.getDeadlineTime() + 1 * 60 * 60 * 1000);
            }
        }
    }
}
