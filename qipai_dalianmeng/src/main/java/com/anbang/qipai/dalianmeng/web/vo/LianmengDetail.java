package com.anbang.qipai.dalianmeng.web.vo;

/**
 * 联盟详情
 */
public class LianmengDetail {
    private String lianmengId;//联盟id
    private String name;//联盟昵称
    private int lianmengCount;//人数
    private int hehuorenCount;//合伙人人数
    private String mengzhuId;//盟主id
    private String nickname;//盟主昵称
    private String createTime;//创建时间

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

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

    public int getLianmengCount() {
        return lianmengCount;
    }

    public void setLianmengCount(int lianmengCount) {
        this.lianmengCount = lianmengCount;
    }

    public int getHehuorenCount() {
        return hehuorenCount;
    }

    public void setHehuorenCount(int hehuorenCount) {
        this.hehuorenCount = hehuorenCount;
    }

    public String getMengzhuId() {
        return mengzhuId;
    }

    public void setMengzhuId(String mengzhuId) {
        this.mengzhuId = mengzhuId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
