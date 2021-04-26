package com.anbang.qipai.dalianmeng.web.vo;

public class AgentVO {

    private String memberId;//玩家id
    private String nickname;// 推广员昵称
    private String headimgurl;// 头像url

    private int zuanshi;//钻石


    private int lianmengCount;//联盟数量



    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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


    public int getZuanshi() {
        return zuanshi;
    }

    public void setZuanshi(int zuanshi) {
        this.zuanshi = zuanshi;
    }

    public int getLianmengCount() {
        return lianmengCount;
    }

    public void setLianmengCount(int lianmengCount) {
        this.lianmengCount = lianmengCount;
    }
}
