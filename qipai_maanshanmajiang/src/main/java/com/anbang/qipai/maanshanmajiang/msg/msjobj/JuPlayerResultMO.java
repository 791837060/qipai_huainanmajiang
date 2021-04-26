package com.anbang.qipai.maanshanmajiang.msg.msjobj;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MaanshanMajiangJuPlayerResult;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;

public class JuPlayerResultMO {

    private String playerId;
    private String nickname;
    private String headimgurl;
    private int huCount;
    private int caishenCount;
    private double totalScore;
    private int zimoCount;
    private int fangPaoCount;

    public JuPlayerResultMO(MaanshanMajiangJuPlayerResult juPlayerResult, MajiangGamePlayerDbo majiangGamePlayerDbo) {
        playerId = majiangGamePlayerDbo.getPlayerId();
        nickname = majiangGamePlayerDbo.getNickname();
        headimgurl = majiangGamePlayerDbo.getHeadimgurl();
        huCount = juPlayerResult.getHuCount();
        caishenCount = juPlayerResult.getCaishenCount();
        zimoCount = juPlayerResult.getZimoCount();
        fangPaoCount = juPlayerResult.getFangPaoCount();
        totalScore = juPlayerResult.getTotalScore();
    }

    public JuPlayerResultMO(MajiangGamePlayerDbo majiangGamePlayerDbo) {
        playerId = majiangGamePlayerDbo.getPlayerId();
        nickname = majiangGamePlayerDbo.getNickname();
        headimgurl = majiangGamePlayerDbo.getHeadimgurl();
        huCount = 0;
        caishenCount = 0;
        zimoCount = 0;
        fangPaoCount = 0;
        totalScore = 0;
    }

    public JuPlayerResultMO() {

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

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

}
