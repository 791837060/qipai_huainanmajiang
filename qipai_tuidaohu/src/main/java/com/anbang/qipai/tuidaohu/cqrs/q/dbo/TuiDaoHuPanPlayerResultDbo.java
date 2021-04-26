package com.anbang.qipai.tuidaohu.cqrs.q.dbo;

import com.anbang.qipai.tuidaohu.cqrs.c.domain.TuiDaoHuPanPlayerResult;
import com.dml.majiang.player.valueobj.MajiangPlayerValueObject;

public class TuiDaoHuPanPlayerResultDbo {
    private String playerId;
    private TuiDaoHuPanPlayerResult playerResult;
    private MajiangPlayerValueObject player;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public TuiDaoHuPanPlayerResult getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(TuiDaoHuPanPlayerResult playerResult) {
        this.playerResult = playerResult;
    }

    public MajiangPlayerValueObject getPlayer() {
        return player;
    }

    public void setPlayer(MajiangPlayerValueObject player) {
        this.player = player;
    }

}
