package com.anbang.qipai.qinyouquan.plan.bean.result.puke;

import com.anbang.qipai.qinyouquan.plan.bean.result.GameJuPlayerResult;

import java.util.Map;

public class BijiJuPlayerResult implements GameJuPlayerResult {
    private String playerId;
    private String nickname;
    private String headimgurl;
    private int tspx;// 特殊牌型
    //	private int qld;// 全垒打
    private int totalScore;
    private String superiorMemberId;
    private int diamondCost;

    public BijiJuPlayerResult(Map juPlayerResult) {
        this.playerId = (String) juPlayerResult.get("playerId");
        this.nickname = (String) juPlayerResult.get("nickname");
        this.headimgurl = (String) juPlayerResult.get("headimgurl");
        this.tspx = ((Double) juPlayerResult.get("tspx")).intValue();
//		this.qld = ((Double) juPlayerResult.get("qld")).intValue();
        this.totalScore = ((Double) juPlayerResult.get("totalScore")).intValue();
    }

    public BijiJuPlayerResult() {

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

    public int getTspx() {
        return tspx;
    }

    public void setTspx(int tspx) {
        this.tspx = tspx;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getSuperiorMemberId() {
        return superiorMemberId;
    }

    public void setSuperiorMemberId(String superiorMemberId) {
        this.superiorMemberId = superiorMemberId;
    }

    public int getDiamondCost() {
        return diamondCost;
    }

    public void setDiamondCost(int diamondCost) {
        this.diamondCost = diamondCost;
    }
}
