package com.anbang.qipai.biji.web.vo;

import com.anbang.qipai.biji.cqrs.q.dbo.BijiPanPlayerResultDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.PukeGamePlayerDbo;

public class BijiPanPlayerResultVO {
    /**
     * 玩家ID
     */
    private String playerId;
    /**
     * 玩家昵称
     */
    private String nickname;
    /**
     * 玩家头像url
     */
    private String headimgurl;
    /**
     * 一盘结算分
     */
    private BijiJiesuanScoreVO score;
    /**
     * 总分
     */
    private double totalScore;
    /**
     * 弃牌
     */
    private boolean qipai;

    public BijiPanPlayerResultVO() {

    }

    public BijiPanPlayerResultVO(PukeGamePlayerDbo playerDbo, BijiPanPlayerResultDbo panPlayerResult) {
        playerId = playerDbo.getPlayerId();
        nickname = playerDbo.getNickname();
        headimgurl = playerDbo.getHeadimgurl();
        score = new BijiJiesuanScoreVO(panPlayerResult.getPlayerResult().getJiesuanScore());
        totalScore = panPlayerResult.getPlayerResult().getTotalScore();
        qipai = panPlayerResult.getPlayer().isQipai();
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

    public BijiJiesuanScoreVO getScore() {
        return score;
    }

    public void setScore(BijiJiesuanScoreVO score) {
        this.score = score;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isQipai() {
        return qipai;
    }

    public void setQipai(boolean qipai) {
        this.qipai = qipai;
    }
}
