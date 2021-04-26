package com.anbang.qipai.qinyouquan.cqrs.q.dbo;

/**
 * 大联盟
 */
public class AllianceDbo {
    private String id;
    private String name;//名称
    private String mengzhu;//盟主
    private String headimgurl;// 头像url
    private String nickname;// 推广员昵称
    private String desc;//描述
    private long createTime;//创建时间
    private boolean renshuHide = false;
    private boolean kongzhuoqianzhi = false;
    private boolean nicknameHide = false;
    private boolean idHide = false;
    private boolean banAlliance = false;
    private boolean zhuomanHide = false;
    private Integer buzhunbeituichushichang=0;
    private Boolean zidongzhunbei=false;
    private Boolean lianmengIdHide=false;


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

    public String getMengzhu() {
        return mengzhu;
    }

    public void setMengzhu(String mengzhu) {
        this.mengzhu = mengzhu;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isRenshuHide() {
        return renshuHide;
    }

    public void setRenshuHide(boolean renshuHide) {
        this.renshuHide = renshuHide;
    }

    public boolean isKongzhuoqianzhi() {
        return kongzhuoqianzhi;
    }

    public void setKongzhuoqianzhi(boolean kongzhuoqianzhi) {
        this.kongzhuoqianzhi = kongzhuoqianzhi;
    }

    public boolean isNicknameHide() {
        return nicknameHide;
    }

    public void setNicknameHide(boolean nicknameHide) {
        this.nicknameHide = nicknameHide;
    }

    public boolean isIdHide() {
        return idHide;
    }

    public void setIdHide(boolean idHide) {
        this.idHide = idHide;
    }

    public boolean isBanAlliance() {
        return banAlliance;
    }

    public void setBanAlliance(boolean banAlliance) {
        this.banAlliance = banAlliance;
    }

    public boolean isZhuomanHide() {
        return zhuomanHide;
    }

    public void setZhuomanHide(boolean zhuomanHide) {
        this.zhuomanHide = zhuomanHide;
    }

    public Integer getBuzhunbeituichushichang() {
        return buzhunbeituichushichang;
    }

    public void setBuzhunbeituichushichang(Integer buzhunbeituichushichang) {
        this.buzhunbeituichushichang = buzhunbeituichushichang;
    }

    public Boolean getZidongzhunbei() {
        return zidongzhunbei;
    }

    public void setZidongzhunbei(Boolean zidongzhunbei) {
        this.zidongzhunbei = zidongzhunbei;
    }

    public Boolean getLianmengIdHide() {
        return lianmengIdHide;
    }

    public void setLianmengIdHide(Boolean lianmengIdHide) {
        this.lianmengIdHide = lianmengIdHide;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
}
