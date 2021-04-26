package com.anbang.qipai.biji.cqrs.c.domain.result;

public class BijiPanPlayerResult {
    /**
     * 玩家ID
     */
    private String playerId;
    /**
     * 玩家结算
     */
    private BijiJiesuanScore jiesuanScore;
    /**
     * 玩家总分
     */
    private double totalScore;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public BijiJiesuanScore getJiesuanScore() {
        return jiesuanScore;
    }

    public void setJiesuanScore(BijiJiesuanScore jiesuanScore) {
        this.jiesuanScore = jiesuanScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

}
