package com.anbang.qipai.huainanmajiang.cqrs.q.dbo;

import com.anbang.qipai.huainanmajiang.cqrs.c.domain.ZongyangMajiangPanPlayerResult;
import com.dml.majiang.player.valueobj.MajiangPlayerValueObject;

public class TuiDaoHuPanPlayerResultDbo {
    private String playerId;
    private ZongyangMajiangPanPlayerResult playerResult;
    private MajiangPlayerValueObject player;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public ZongyangMajiangPanPlayerResult getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(ZongyangMajiangPanPlayerResult playerResult) {
        this.playerResult = playerResult;
    }

    public MajiangPlayerValueObject getPlayer() {
        return player;
    }

    public void setPlayer(MajiangPlayerValueObject player) {
        this.player = player;
    }

}
