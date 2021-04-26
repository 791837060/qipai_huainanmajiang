package com.anbang.qipai.maanshanmajiang.cqrs.q.dbo;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MaanshanMajiangPanPlayerResult;
import com.dml.majiang.player.valueobj.MajiangPlayerValueObject;

public class MaanshanMajiangPanPlayerResultDbo {
    private String playerId;
    private MaanshanMajiangPanPlayerResult playerResult;
    private MajiangPlayerValueObject player;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public MaanshanMajiangPanPlayerResult getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(MaanshanMajiangPanPlayerResult playerResult) {
        this.playerResult = playerResult;
    }

    public MajiangPlayerValueObject getPlayer() {
        return player;
    }

    public void setPlayer(MajiangPlayerValueObject player) {
        this.player = player;
    }

}
