package com.anbang.qipai.qinyouquan.cqrs.q.dbo;

public class MemberDbo {
    private String id;
    private String nickname;// 会员昵称
    private String headimgurl;// 头像url
    private boolean dalianmeng;
    private boolean qinyouquan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isDalianmeng() {
        return dalianmeng;
    }

    public void setDalianmeng(boolean dalianmeng) {
        this.dalianmeng = dalianmeng;
    }

    public boolean isQinyouquan() {
        return qinyouquan;
    }

    public void setQinyouquan(boolean qinyouquan) {
        this.qinyouquan = qinyouquan;
    }
}
