package com.anbang.qipai.qinyouquan.web.vo;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.Identity;

public class GamesDetailsVO {
    private Identity identity;//身份
    private String nickname;//昵称
    private String headimgurl;//头像
    private String memberId;
    private int TotalGames;//总局数
    private int TotalDayingjia;//总大赢家数

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getTotalGames() {
        return TotalGames;
    }

    public void setTotalGames(int totalGames) {
        TotalGames = totalGames;
    }

    public int getTotalDayingjia() {
        return TotalDayingjia;
    }

    public void setTotalDayingjia(int totalDayingjia) {
        TotalDayingjia = totalDayingjia;
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
}
