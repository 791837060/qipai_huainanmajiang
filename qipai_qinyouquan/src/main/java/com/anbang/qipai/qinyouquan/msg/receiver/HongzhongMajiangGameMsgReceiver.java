package com.anbang.qipai.qinyouquan.msg.receiver;


import com.anbang.qipai.qinyouquan.cqrs.c.service.GameTableCmdService;
import com.anbang.qipai.qinyouquan.msg.msjobs.CommonMO;
import com.anbang.qipai.qinyouquan.msg.sink.HongzhongMajiangGameSink;
import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameTable;
import com.anbang.qipai.qinyouquan.plan.service.MemberLatAndLonService;
import com.anbang.qipai.qinyouquan.plan.service.PlayService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.List;
import java.util.Map;

@EnableBinding(HongzhongMajiangGameSink.class)
public class HongzhongMajiangGameMsgReceiver {
    @Autowired
    private PlayService playService;

    @Autowired
    private GameTableCmdService gameTableCmdService;

    @Autowired
    private MemberLatAndLonService memberLatAndLonService;
    private Gson gson = new Gson();

    @StreamListener(HongzhongMajiangGameSink.HONGZHONGMAJIANGGAME)
    public void receive(CommonMO mo) {
        String msg = mo.getMsg();
        if ("playerQuit".equals(msg)) {// 有人退出游戏
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            String playerId = (String) data.get("playerId");
            GameTable table = playService.findTableByGameAndServerGameGameId(Game.hongzhongMajiang, gameId);
            if (table != null) {
                playService.gamePlayerQuitQame(Game.hongzhongMajiang, gameId, playerId);
            }
            memberLatAndLonService.deleteMemberLatAndLon(playerId);
        }
        if ("ju canceled".equals(msg)) {// 取消游戏
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameTable gameTable = playService.findTableByGameAndServerGameGameId(Game.hongzhongMajiang, gameId);
            if (gameTable != null) {
                List<String> players = gameTable.getPlayers();
                if (players!=null){
                    for (String player : players) {
                        memberLatAndLonService.deleteMemberLatAndLon(player);
                    }
                }
                gameTableCmdService.removeTable(gameTable.getNo());
                playService.gameTableFinished(Game.hongzhongMajiang, gameId);
            }
        }

        if ("ju finished".equals(msg)) {// 一局游戏结束
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameTable gameTable = playService.findTableByGameAndServerGameGameId(Game.hongzhongMajiang, gameId);
            if (gameTable != null) {
                List<String> players = gameTable.getPlayers();
                if (players!=null){
                    for (String player : players) {
                        memberLatAndLonService.deleteMemberLatAndLon(player);
                    }
                }
                gameTableCmdService.removeTable(gameTable.getNo());
                playService.gameTableFinished(Game.hongzhongMajiang, gameId);
            }
        }
        if ("pan finished".equals(msg)) {// 一盘游戏结束
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            int no = (int) data.get("no");
            List playerIds = (List) data.get("playerIds");
            GameTable gameTable = playService.findTableByGameAndServerGameGameId(Game.hongzhongMajiang, gameId);
            if (gameTable != null) {
                playService.panFinished(Game.hongzhongMajiang, gameId, no, playerIds);
            }
        }
        if ("game start".equals(msg)) {// 游戏开始
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameTable gameTable = playService.findTableByGameAndServerGameGameId(Game.hongzhongMajiang, gameId);
            if (gameTable != null) {
                playService.gameTablePlaying(Game.hongzhongMajiang, gameId);
            }
        }
        if ("game delay".equals(msg)) {// 游戏延时
            Map data = (Map) mo.getData();
            String gameId = (String) data.get("gameId");
            GameTable gameTable = playService.findTableByGameAndServerGameGameId(Game.hongzhongMajiang, gameId);
            if (gameTable != null) {
                // 延时1小时
                playService.delayGameTable(Game.hongzhongMajiang, gameId, gameTable.getDeadlineTime() + 1 * 60 * 60 * 1000);
            }
        }
    }

}
