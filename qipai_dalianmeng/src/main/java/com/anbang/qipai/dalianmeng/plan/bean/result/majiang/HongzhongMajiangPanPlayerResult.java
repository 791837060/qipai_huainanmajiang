package com.anbang.qipai.dalianmeng.plan.bean.result.majiang;


import com.anbang.qipai.dalianmeng.plan.bean.result.GamePanPlayerResult;

import java.util.Map;

public class HongzhongMajiangPanPlayerResult implements GamePanPlayerResult {
    private String playerId;// 玩家id
    private String nickname;// 玩家昵称
    private double score;// 一盘总分

    public HongzhongMajiangPanPlayerResult() {

    }

    public HongzhongMajiangPanPlayerResult(Map panPlayerResult) {
        this.playerId = (String) panPlayerResult.get("playerId");
        this.nickname = (String) panPlayerResult.get("nickname");
        this.score = ((Double) panPlayerResult.get("score"));
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
