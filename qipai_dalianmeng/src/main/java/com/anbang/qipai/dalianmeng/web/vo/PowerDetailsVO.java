package com.anbang.qipai.dalianmeng.web.vo;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.Identity;

public class PowerDetailsVO {
    private String nickname;
    private String headimgurl;
    private Identity identity;
    private String memberId;
    private double score;
    private double totalScore;
    private double power;
    private double totalPower;

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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(double totalPower) {
        this.totalPower = totalPower;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }
}
