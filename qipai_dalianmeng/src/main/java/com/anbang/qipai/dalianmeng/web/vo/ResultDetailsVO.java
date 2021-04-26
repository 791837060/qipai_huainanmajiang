package com.anbang.qipai.dalianmeng.web.vo;

public class ResultDetailsVO {
    private String nickname;
    private String headimgurl;
    private String memberId;
    private int totalGames;
    private int totalDayingjia;
    private int totalPower;



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

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getTotalDayingjia() {
        return totalDayingjia;
    }

    public void setTotalDayingjia(int totalDayingjia) {
        this.totalDayingjia = totalDayingjia;
    }

    public int getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(int totalPower) {
        this.totalPower = totalPower;
    }
}
