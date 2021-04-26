package com.anbang.qipai.biji.web.vo;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJuPlayerResult;
import com.anbang.qipai.biji.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.PukeGamePlayerDbo;

public class BijiJuPlayerResultVO {
    private String playerId;
    private String nickname;
    private String headimgurl;
    private int tspx;            //特殊牌型
    private int qld;            //全垒打
    private double totalScore;

    public BijiJuPlayerResultVO(PukeGamePlayerDbo playerDbo) {
        playerId = playerDbo.getPlayerId();
        nickname = playerDbo.getNickname();
        headimgurl = playerDbo.getHeadimgurl();
        tspx = 0;
        qld = 0;
        totalScore = 0;
    }

    public BijiJuPlayerResultVO(BijiJuPlayerResult juPlayerResult, PukeGamePlayerDbo playerDbo) {
        playerId = playerDbo.getPlayerId();
        nickname = playerDbo.getNickname();
        headimgurl = playerDbo.getHeadimgurl();
        tspx = juPlayerResult.getTspx();
        qld = juPlayerResult.getQld();
        totalScore = juPlayerResult.getTotalScore();
    }

    public BijiJuPlayerResultVO(BijiJuPlayerResult juPlayerResult, PukeGamePlayerDbo playerDbo, PukeGameDbo pukeGameDbo) {
        playerId = playerDbo.getPlayerId();
        nickname = playerDbo.getNickname();
        headimgurl = playerDbo.getHeadimgurl();
        tspx = juPlayerResult.getTspx();
        qld = juPlayerResult.getQld();
        if (pukeGameDbo.getOptionalPlay().isJinyuanzi()) {
            totalScore = juPlayerResult.getTotalScore();
        } else {
            totalScore = 0d;
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

    public int getTspx() {
        return tspx;
    }

    public void setTspx(int tspx) {
        this.tspx = tspx;
    }

    public int getQld() {
        return qld;
    }

    public void setQld(int qld) {
        this.qld = qld;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

}
