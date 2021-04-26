package com.anbang.qipai.game.plan.bean.historicalresult.zipai;

import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;

import java.util.Map;

public class HuangshibaJuPlayerResult implements GameJuPlayerResult {
    private String playerId;
    private String nickname;
    private String headimgurl;
    private int huCount;
    private double totalScore;

    public HuangshibaJuPlayerResult(Map juPlayerResult) {
        this.playerId = (String) juPlayerResult.get("playerId");
        this.nickname = (String) juPlayerResult.get("nickname");
        this.headimgurl = (String) juPlayerResult.get("headimgurl");
        this.huCount = ((Double) juPlayerResult.get("huCount")).intValue();
        this.totalScore = ((Double) juPlayerResult.get("totalScore")).intValue();
    }

    public HuangshibaJuPlayerResult() {

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

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public int getHuCount() {
        return huCount;
    }

    public void setHuCount(int huCount) {
        this.huCount = huCount;
    }



    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
    @Override
    public String playerId() {
        return playerId;
    }

    @Override
    public String nickname() {
        return nickname;
    }

    @Override
    public String headimgurl() {
        return headimgurl;
    }

    @Override
    public double totalScore() {
        return totalScore;
    }
}
