package com.anbang.qipai.qinyouquan.web.vo;

/**
 * 大联盟消耗
 */
public class LianmengCost {
    private String mengzhuId;//盟主ID
    private String mengzhuNickname;//盟主昵称
    private String mengzhuHeadimgurl;//盟主头像
    private String lianmengId;//联盟id
    private String name;//联盟昵称
    private int lianmengyushiCost = 0;//联盟消耗的钻石
    private int juCost = 0;//总局数

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLianmengyushiCost() {
        return lianmengyushiCost;
    }

    public void setLianmengyushiCost(int lianmengyushiCost) {
        this.lianmengyushiCost = lianmengyushiCost;
    }

    public int getJuCost() {
        return juCost;
    }

    public void setJuCost(int juCost) {
        this.juCost = juCost;
    }


    public String getMengzhuId() {
        return mengzhuId;
    }

    public void setMengzhuId(String mengzhuId) {
        this.mengzhuId = mengzhuId;
    }

    public String getMengzhuNickname() {
        return mengzhuNickname;
    }

    public void setMengzhuNickname(String mengzhuNickname) {
        this.mengzhuNickname = mengzhuNickname;
    }

    public String getMengzhuHeadimgurl() {
        return mengzhuHeadimgurl;
    }

    public void setMengzhuHeadimgurl(String mengzhuHeadimgurl) {
        this.mengzhuHeadimgurl = mengzhuHeadimgurl;
    }
}
