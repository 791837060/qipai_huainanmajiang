package com.anbang.qipai.paodekuai.cqrs.c.domain;

import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGameValueObject;
import com.dml.paodekuai.ju.JuResult;
import com.dml.paodekuai.wanfa.OptionalPlay;

import java.util.HashMap;
import java.util.Map;

public class PukeGameValueObject extends FixedPlayersMultipanAndVotetofinishGameValueObject {

    private int panshu;
    private int renshu;
    private double difen;
    private OptionalPlay optionalPlay;
    private Map<String, Double> playeTotalScoreMap = new HashMap<>();
    private JuResult juResult;
    private int powerLimit;
    private String lianmengId;

    public PukeGameValueObject(PukeGame pukeGame) {
        super(pukeGame);
        panshu = pukeGame.getPanshu();
        renshu = pukeGame.getRenshu();
        difen = pukeGame.getDifen();
        optionalPlay = pukeGame.getOptionalPlay();
        powerLimit = pukeGame.getPowerLimit();
        lianmengId = pukeGame.getLianmengId();
        playeTotalScoreMap.putAll(pukeGame.getPlayerTotalScoreMap());
        if (pukeGame.getJu() != null) {
            juResult = pukeGame.getJu().getJuResult();
        }
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

    public double getDifen() {
        return difen;
    }

    public void setDifen(double difen) {
        this.difen = difen;
    }

    public Map<String, Double> getPlayeTotalScoreMap() {
        return playeTotalScoreMap;
    }

    public void setPlayeTotalScoreMap(Map<String, Double> playeTotalScoreMap) {
        this.playeTotalScoreMap = playeTotalScoreMap;
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

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }
}
