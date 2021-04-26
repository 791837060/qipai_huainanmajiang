package com.anbang.qipai.guandan.web.vo;

import com.anbang.qipai.guandan.cqrs.c.domain.result.GuandanJuPlayerResult;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGamePlayerDbo;

public class GuandanJuPlayerResultVO {
    private String playerId;
    private String nickname;
    private String headimgurl;
    private int shuangkouCount;
    private int dankouCount;
    private int pingkouCount;
    private int maxXianshu;
    private double totalScore;

    public GuandanJuPlayerResultVO(PukeGamePlayerDbo playerDbo) {
        playerId = playerDbo.getPlayerId();
        nickname = playerDbo.getNickname();
        headimgurl = playerDbo.getHeadimgurl();
        shuangkouCount = 0;
        dankouCount = 0;
        pingkouCount = 0;
        maxXianshu = 0;
        totalScore = 0d;
    }

    public GuandanJuPlayerResultVO(GuandanJuPlayerResult juPlayerResult, PukeGamePlayerDbo playerDbo) {
        playerId = playerDbo.getPlayerId();
        nickname = playerDbo.getNickname();
        headimgurl = playerDbo.getHeadimgurl();
        shuangkouCount = juPlayerResult.getShuangkouCount();
        dankouCount = juPlayerResult.getDankouCount();
        pingkouCount = juPlayerResult.getPingkouCount();
        maxXianshu = juPlayerResult.getMaxXianshu();
//		totalScore = juPlayerResult.getTotalScore();
        totalScore = playerDbo.getTotalScore();
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

    public int getShuangkouCount() {
        return shuangkouCount;
    }

    public void setShuangkouCount(int shuangkouCount) {
        this.shuangkouCount = shuangkouCount;
    }

    public int getDankouCount() {
        return dankouCount;
    }

    public void setDankouCount(int dankouCount) {
        this.dankouCount = dankouCount;
    }

    public int getPingkouCount() {
        return pingkouCount;
    }

    public void setPingkouCount(int pingkouCount) {
        this.pingkouCount = pingkouCount;
    }

    public int getMaxXianshu() {
        return maxXianshu;
    }

    public void setMaxXianshu(int maxXianshu) {
        this.maxXianshu = maxXianshu;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}
