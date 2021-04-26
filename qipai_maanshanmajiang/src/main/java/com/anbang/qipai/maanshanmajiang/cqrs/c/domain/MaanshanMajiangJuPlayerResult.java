package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

public class MaanshanMajiangJuPlayerResult {

    private String playerId;
    private int huCount;
    private int caishenCount;
    private int zimoCount;
    private int fangPaoCount;
    private Double totalScore;

    public void increaseHuCount() {
        huCount++;
    }

    public void increaseCaishenCount(int amount) {
        caishenCount += amount;
    }

    public void increaseZiMoCount() {
        zimoCount++;
    }

    public void increaseFangPaoCount() {
        fangPaoCount++;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getHuCount() {
        return huCount;
    }

    public void setHuCount(int huCount) {
        this.huCount = huCount;
    }

    public int getCaishenCount() {
        return caishenCount;
    }

    public void setCaishenCount(int caishenCount) {
        this.caishenCount = caishenCount;
    }

    public int getZimoCount() {
        return zimoCount;
    }

    public void setZimoCount(int zimoCount) {
        this.zimoCount = zimoCount;
    }

    public int getFangPaoCount() {
        return fangPaoCount;
    }

    public void setFangPaoCount(int fangPaoCount) {
        this.fangPaoCount = fangPaoCount;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

}
