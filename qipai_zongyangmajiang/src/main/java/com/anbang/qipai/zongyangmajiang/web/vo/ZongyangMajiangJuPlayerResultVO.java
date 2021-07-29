package com.anbang.qipai.zongyangmajiang.web.vo;

import com.anbang.qipai.zongyangmajiang.cqrs.c.domain.ZongyangMajiangJuPlayerResult;
import com.anbang.qipai.zongyangmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;

public class ZongyangMajiangJuPlayerResultVO {

    private String playerId;
    private String nickname;
    private String headimgurl;
    private int huCount;
    private int caishenCount;
    private int zimoCount;
    private int fangPaoCount;
    private Double totalScore;

    public ZongyangMajiangJuPlayerResultVO(ZongyangMajiangJuPlayerResult juPlayerResult, MajiangGamePlayerDbo majiangGamePlayerDbo) {
        playerId = majiangGamePlayerDbo.getPlayerId();
        nickname = majiangGamePlayerDbo.getNickname();
        headimgurl = majiangGamePlayerDbo.getHeadimgurl();
        huCount = juPlayerResult.getHuCount();
        caishenCount = juPlayerResult.getCaishenCount();
        zimoCount = juPlayerResult.getZimoCount();
        fangPaoCount = juPlayerResult.getFangPaoCount();
        totalScore = juPlayerResult.getTotalScore();
    }

    public ZongyangMajiangJuPlayerResultVO(MajiangGamePlayerDbo majiangGamePlayerDbo) {
        playerId = majiangGamePlayerDbo.getPlayerId();
        nickname = majiangGamePlayerDbo.getNickname();
        headimgurl = majiangGamePlayerDbo.getHeadimgurl();
        huCount = 0;
        caishenCount = 0;
        zimoCount = 0;
        fangPaoCount = 0;
        totalScore = 0d;
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

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}
