package com.anbang.qipai.biji.cqrs.c.domain.result;

public class BijiJuPlayerResult {
    private String playerId;    //玩家ID
    private int tspx;           //特殊牌型
    private int qld;            //全垒打
    private double totalScore;  //总分

    public void increaseTspx() {
        tspx++;
    }

    public void increaseQld() {
        qld++;
    }

    public void increaseTotalScore(double score) {
        totalScore += score;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getTspx() {
        return tspx;
    }

    public void setTspx(int tspx) {
        this.tspx = tspx;
    }

    public int getQld() {
        return qld;
    }

    public void setQld(int qld) {
        this.qld = qld;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

}
