package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import com.dml.majiang.ju.result.JuResult;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGameValueObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MajiangGameValueObject extends FixedPlayersMultipanAndVotetofinishGameValueObject {
    /**
     * 盘数
     */
    private int panshu;
    /**
     * 人数
     */
    private int renshu;
    /**
     * 麻将可选玩法
     */
    private OptionalPlay optionalPlay;
    /**
     * 玩家总分
     */
    private Map<String, Double> playeTotalScoreMap = new HashMap<>();
    private Set<String> xipaiPlayerIds = new HashSet<>();
    private JuResult juResult;
    private Double difen;
    private int powerLimit;
    private String lianmengId;

    public MajiangGameValueObject(MajiangGame majiangGame) {
        super(majiangGame);
        panshu = majiangGame.getPanshu();
        renshu = majiangGame.getRenshu();
        optionalPlay = majiangGame.getOptionalPlay();
        playeTotalScoreMap.putAll(majiangGame.getPlayeTotalScoreMap());
        xipaiPlayerIds = new HashSet<>(majiangGame.getXipaiPlayerIds());
        powerLimit = majiangGame.getPowerLimit();
        difen = majiangGame.getDifen();
        if (majiangGame.getJu() != null) {
            juResult = majiangGame.getJu().getJuResult();
        }
        lianmengId = majiangGame.getLianmengId();
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

    public Set<String> getXipaiPlayerIds() {
        return xipaiPlayerIds;
    }

    public void setXipaiPlayerIds(Set<String> xipaiPlayerIds) {
        this.xipaiPlayerIds = xipaiPlayerIds;
    }


    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }

    public Double getDifen() {
        return difen;
    }

    public void setDifen(Double difen) {
        this.difen = difen;
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
