package com.anbang.qipai.dalianmeng.web.vo;

public class AllianceVO {
    private String id;
    private String name;//名称
    private String desc;//描述
    private int onlineCount;//在线人数
    private int yushiAccount;//联盟玉石
    private double powerAccount;//联盟能量
    private double contributionValue; //贡献值
    private boolean join;//加入
    private int renshu;
    private boolean renshuHide;
    private boolean kongzhuoqianzhi;
    private boolean nicknameHide;
    private boolean idHide;
    private boolean banAlliance;
    private boolean zhuomanHide;
    private Integer buzhunbeituichushichang;
    private boolean zidongzhunbei;
    private boolean zidongkaishi;
    private boolean lianmengIdHide;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public boolean isJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }

    public int getYushiAccount() {
        return yushiAccount;
    }

    public void setYushiAccount(int yushiAccount) {
        this.yushiAccount = yushiAccount;
    }

    public double getPowerAccount() {
        return powerAccount;
    }

    public void setPowerAccount(double powerAccount) {
        this.powerAccount = powerAccount;
    }

    public double getContributionValue() {
        return contributionValue;
    }

    public void setContributionValue(double contributionValue) {
        this.contributionValue = contributionValue;
    }

    public int getRenshu() {
        return renshu;
    }

    public void setRenshu(int renshu) {
        this.renshu = renshu;
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

    public boolean isZidongzhunbei() {
        return zidongzhunbei;
    }

    public void setZidongzhunbei(boolean zidongzhunbei) {
        this.zidongzhunbei = zidongzhunbei;
    }

    public boolean isZidongkaishi() {
        return zidongkaishi;
    }

    public void setZidongkaishi(boolean zidongkaishi) {
        this.zidongkaishi = zidongkaishi;
    }

    public boolean isLianmengIdHide() {
        return lianmengIdHide;
    }

    public void setLianmengIdHide(boolean lianmengIdHide) {
        this.lianmengIdHide = lianmengIdHide;
    }
}
