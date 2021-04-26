package com.anbang.qipai.admin.plan.bean.historicalresult.majiang;


import com.anbang.qipai.admin.plan.bean.historicalresult.GamePanPlayerResult;

import java.util.Map;

public class YangzhouMajiangPanPlayerResult implements GamePanPlayerResult {
    private String playerId;// 玩家id
    private String nickname;// 玩家昵称
    private double score;// 一盘总分

    public YangzhouMajiangPanPlayerResult() {

    }

    public YangzhouMajiangPanPlayerResult(Map panPlayerResult) {
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
