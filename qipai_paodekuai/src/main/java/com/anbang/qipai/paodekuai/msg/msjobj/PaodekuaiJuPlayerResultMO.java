package com.anbang.qipai.paodekuai.msg.msjobj;

import com.anbang.qipai.paodekuai.cqrs.c.domain.result.PaodekuaiJuPlayerResult;
import com.anbang.qipai.paodekuai.cqrs.q.dbo.PukeGamePlayerDbo;
import com.dml.paodekuai.wanfa.OptionalPlay;

public class PaodekuaiJuPlayerResultMO {
    private String playerId;
    private String nickname;
    private String headimgurl;
    private double totalScore;

    private int danguanCount;
    private int shuangguanCount;
    private int boomCount;
    private double maxScore;

    public PaodekuaiJuPlayerResultMO(PukeGamePlayerDbo playerDbo) {
        playerId = playerDbo.getPlayerId();
        nickname = playerDbo.getNickname();
        headimgurl = playerDbo.getHeadimgurl();
        totalScore = 0;
    }

    public PaodekuaiJuPlayerResultMO(PaodekuaiJuPlayerResult juPlayerResult, PukeGamePlayerDbo playerDbo, OptionalPlay optionalPlay) {
        playerId = playerDbo.getPlayerId();
        nickname = playerDbo.getNickname();
        headimgurl = playerDbo.getHeadimgurl();
        danguanCount = juPlayerResult.getDanguanCount();
        shuangguanCount = juPlayerResult.getShuangguanCount();
        boomCount = juPlayerResult.getBoomCount();
        maxScore = juPlayerResult.getMaxScore();
        if (optionalPlay.isJinyuanzi()) {
            totalScore = juPlayerResult.getTotalScore() - optionalPlay.getYuanzifen();
        } else {
            totalScore = juPlayerResult.getTotalScore();
        }
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

    public int getDanguanCount() {
        return danguanCount;
    }

    public void setDanguanCount(int danguanCount) {
        this.danguanCount = danguanCount;
    }

    public int getShuangguanCount() {
        return shuangguanCount;
    }

    public void setShuangguanCount(int shuangguanCount) {
        this.shuangguanCount = shuangguanCount;
    }

    public int getBoomCount() {
        return boomCount;
    }

    public void setBoomCount(int boomCount) {
        this.boomCount = boomCount;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }
}
