package com.anbang.qipai.qinyouquan.web.vo;

public class LianmengVO {
    private String id;
    private String name;//名称
    private String headimgurl;// 头像url
    private int memberCount;//成员人数
    private boolean self;//盟主是否是玩家自己
    private boolean lianmengIdHide;
    private boolean renshuHide=false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public boolean isSelf() {
        return self;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }

    public boolean isLianmengIdHide() {
        return lianmengIdHide;
    }

    public void setLianmengIdHide(boolean lianmengIdHide) {
        this.lianmengIdHide = lianmengIdHide;
    }

    public boolean isRenshuHide() {
        return renshuHide;
    }

    public void setRenshuHide(boolean renshuHide) {
        this.renshuHide = renshuHide;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
}
