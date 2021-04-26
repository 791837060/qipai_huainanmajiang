package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import java.util.*;

import com.dml.majiang.ju.result.JuResult;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGameValueObject;

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
    /**
     * 当前倒
     */
    private int currentDao;
    /**
     * 玩家每倒分数
     */
    private Map<String, List<Double>> playeTotalDaoScoreMap;
    /**
     * 外加体外循环分数
     */
    private Map<String, Double> playerTiwaixunhuanScoreMap;

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
        currentDao = majiangGame.getCurrentDao();
        playeTotalDaoScoreMap = majiangGame.getPlayerTotalDaoScoreMap();
        playerTiwaixunhuanScoreMap=majiangGame.getPlayerTiwaixunhuanScoreMap();
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

    public int getCurrentDao() {
        return currentDao;
    }

    public void setCurrentDao(int currentDao) {
        this.currentDao = currentDao;
    }

    public Map<String, List<Double>> getPlayeTotalDaoScoreMap() {
        return playeTotalDaoScoreMap;
    }

    public void setPlayeTotalDaoScoreMap(Map<String, List<Double>> playeTotalDaoScoreMap) {
        this.playeTotalDaoScoreMap = playeTotalDaoScoreMap;
    }

    public Map<String, Double> getPlayerTiwaixunhuanScoreMap() {
        return playerTiwaixunhuanScoreMap;
    }

    public void setPlayerTiwaixunhuanScoreMap(Map<String, Double> playerTiwaixunhuanScoreMap) {
        this.playerTiwaixunhuanScoreMap = playerTiwaixunhuanScoreMap;
    }
}
