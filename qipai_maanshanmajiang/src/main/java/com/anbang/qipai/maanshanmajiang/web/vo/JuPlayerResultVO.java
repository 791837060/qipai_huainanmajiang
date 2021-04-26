package com.anbang.qipai.maanshanmajiang.web.vo;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MaanshanMajiangJuPlayerResult;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGameDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;

import java.util.List;

public class JuPlayerResultVO {

    private String playerId;
    private String nickname;
    private String headimgurl;
    private int huCount;
    private int caishenCount;
    private int zimoCount;
    private int fangPaoCount;
    private Double totalScore;

    /**
     * 玩家每倒分数
     */
    private List<Double> playerDaoScore;

    public JuPlayerResultVO(MaanshanMajiangJuPlayerResult juPlayerResult, MajiangGamePlayerDbo majiangGamePlayerDbo, MajiangGameDbo majiangGameDbo) {
        playerId = majiangGamePlayerDbo.getPlayerId();
        nickname = majiangGamePlayerDbo.getNickname();
        headimgurl = majiangGamePlayerDbo.getHeadimgurl();
        huCount = juPlayerResult.getHuCount();
        caishenCount = juPlayerResult.getCaishenCount();
        zimoCount = juPlayerResult.getZimoCount();
        fangPaoCount = juPlayerResult.getFangPaoCount();
        totalScore = juPlayerResult.getTotalScore();
        playerDaoScore = majiangGameDbo.getPlayeTotalDaoScoreMap().get(majiangGamePlayerDbo.getPlayerId());
    }

    public JuPlayerResultVO(MajiangGamePlayerDbo majiangGamePlayerDbo) {
        playerId = majiangGamePlayerDbo.getPlayerId();
        nickname = majiangGamePlayerDbo.getNickname();
        headimgurl = majiangGamePlayerDbo.getHeadimgurl();
        huCount = 0;
        caishenCount = 0;
        zimoCount = 0;
        fangPaoCount = 0;
        totalScore = 50d;//暂时1倒50分
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

    public List<Double> getPlayerDaoScore() {
        return playerDaoScore;
    }

    public void setPlayerDaoScore(List<Double> playerDaoScore) {
        this.playerDaoScore = playerDaoScore;
    }
}
