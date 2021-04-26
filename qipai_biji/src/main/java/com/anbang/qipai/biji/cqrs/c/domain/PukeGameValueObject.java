package com.anbang.qipai.biji.cqrs.c.domain;

import java.util.HashMap;
import java.util.Map;

import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGameValueObject;
import com.dml.shisanshui.ju.JuResult;
import com.dml.shisanshui.position.Position;

public class PukeGameValueObject extends FixedPlayersMultipanAndVotetofinishGameValueObject {
    private int panshu;
    private int renshu;
    private OptionalPlay optionalPlay;
    private Map<String, Double> playerTotalScoreMap = new HashMap<>();
    private Map<String, Position> playerIdPositionMap = new HashMap<>();
    private JuResult juResult;
    private int powerLimit;

    public PukeGameValueObject(PukeGame game) {
        super(game);
        panshu = game.getPanshu();
        renshu = game.getRenshu();
        optionalPlay = game.getOptionalPlay();
        playerTotalScoreMap.putAll(game.getPlayerTotalScoreMap());
        playerIdPositionMap.putAll(game.getPlayerIdPositionMap());
        if (game.getJu() != null) {
            juResult = game.getJu().getJuResult();
        }
        powerLimit=game.getPowerLimit();
    }

    public int getPanshu() {
        return panshu;
    }

    public void setPanshu(int panshu) {
        this.panshu = panshu;
    }

    public int getRenshu() {
        return renshu;
    }

    public void setRenshu(int renshu) {
        this.renshu = renshu;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }

    public Map<String, Double> getPlayerTotalScoreMap() {
        return playerTotalScoreMap;
    }

    public void setPlayerTotalScoreMap(Map<String, Double> playerTotalScoreMap) {
        this.playerTotalScoreMap = playerTotalScoreMap;
    }

    public Map<String, Position> getPlayerIdPositionMap() {
        return playerIdPositionMap;
    }

    public void setPlayerIdPositionMap(Map<String, Position> playerIdPositionMap) {
        this.playerIdPositionMap = playerIdPositionMap;
    }

    public JuResult getJuResult() {
        return juResult;
    }

    public void setJuResult(JuResult juResult) {
        this.juResult = juResult;
    }

    public int getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(int powerLimit) {
        this.powerLimit = powerLimit;
    }
}
