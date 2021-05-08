package com.anbang.qipai.shouxianmajiang.cqrs.q.dbo;

import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.ShouxianMajiangPanPlayerResult;
import com.dml.majiang.player.valueobj.MajiangPlayerValueObject;

public class ShouxianMajiangPanPlayerResultDbo {
    private String playerId;
    private ShouxianMajiangPanPlayerResult playerResult;
    private MajiangPlayerValueObject player;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public ShouxianMajiangPanPlayerResult getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(ShouxianMajiangPanPlayerResult playerResult) {
        this.playerResult = playerResult;
    }

    public MajiangPlayerValueObject getPlayer() {
        return player;
    }

    public void setPlayer(MajiangPlayerValueObject player) {
        this.player = player;
    }

}
