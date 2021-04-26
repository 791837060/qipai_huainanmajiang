package com.anbang.qipai.dalianmeng.web.vo;

public class MemberDayResultVO {
    private String lianmengId;// 联盟id
    private String referer;//推荐人
    private String playerId;// 玩家id
    private String nickname;// 昵称
    private String headimgurl;// 头像
    private int finishJuCount;//总局数
    private int dayingjiaCount;// 大赢家次数
    private double yushiCost;// 玉石消耗
    private double totalScore;// 总得分

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
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

    public int getFinishJuCount() {
        return finishJuCount;
    }

    public void setFinishJuCount(int finishJuCount) {
        this.finishJuCount = finishJuCount;
    }

    public int getDayingjiaCount() {
        return dayingjiaCount;
    }

    public void setDayingjiaCount(int dayingjiaCount) {
        this.dayingjiaCount = dayingjiaCount;
    }

    public double getYushiCost() {
        return yushiCost;
    }

    public void setYushiCost(double yushiCost) {
        this.yushiCost = yushiCost;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}
