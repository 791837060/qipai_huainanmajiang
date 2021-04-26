package com.anbang.qipai.doudizhu.cqrs.c.domain;

/**
 * 麻将可选玩法（不包括局数人数）
 */
public class OptionalPlay {
    private int buzhunbeituichushichang;//不准备自动开始时长
    private Boolean zidongzhunbei;      //自动准备
    private Boolean zidongkaishi;       //自动开始
    private Double zidongkaishiTime;    //自动开始时间
    private boolean banVoice;           //禁止语音
    private boolean banJiesan;          //禁止解散
    private boolean dairuzongfen;       //带入总分

    private boolean qxp;
    private boolean szfbxp;
    private boolean gps;
    private boolean voice;
    private int fdbs;
    private boolean shuangwangBiqiang;
    private boolean jiaofen;
    private boolean xianshishoupai;     //显示手牌

    public boolean isQxp() {
        return qxp;
    }

    public void setQxp(boolean qxp) {
        this.qxp = qxp;
    }

    public boolean isSzfbxp() {
        return szfbxp;
    }

    public void setSzfbxp(boolean szfbxp) {
        this.szfbxp = szfbxp;
    }

    public boolean isGps() {
        return gps;
    }

    public void setGps(boolean gps) {
        this.gps = gps;
    }

    public boolean isVoice() {
        return voice;
    }

    public void setVoice(boolean voice) {
        this.voice = voice;
    }

    public int getFdbs() {
        return fdbs;
    }

    public void setFdbs(int fdbs) {
        this.fdbs = fdbs;
    }

    public boolean isShuangwangBiqiang() {
        return shuangwangBiqiang;
    }

    public void setShuangwangBiqiang(boolean shuangwangBiqiang) {
        this.shuangwangBiqiang = shuangwangBiqiang;
    }

    public boolean isJiaofen() {
        return jiaofen;
    }

    public void setJiaofen(boolean jiaofen) {
        this.jiaofen = jiaofen;
    }

    public int getBuzhunbeituichushichang() {
        return buzhunbeituichushichang;
    }

    public void setBuzhunbeituichushichang(int buzhunbeituichushichang) {
        this.buzhunbeituichushichang = buzhunbeituichushichang;
    }

    public Boolean getZidongzhunbei() {
        return zidongzhunbei;
    }

    public void setZidongzhunbei(Boolean zidongzhunbei) {
        this.zidongzhunbei = zidongzhunbei;
    }

    public Boolean getZidongkaishi() {
        return zidongkaishi;
    }

    public void setZidongkaishi(Boolean zidongkaishi) {
        this.zidongkaishi = zidongkaishi;
    }

    public Double getZidongkaishiTime() {
        return zidongkaishiTime;
    }

    public void setZidongkaishiTime(Double zidongkaishiTime) {
        this.zidongkaishiTime = zidongkaishiTime;
    }

    public boolean isBanVoice() {
        return banVoice;
    }

    public void setBanVoice(boolean banVoice) {
        this.banVoice = banVoice;
    }

    public boolean isBanJiesan() {
        return banJiesan;
    }

    public void setBanJiesan(boolean banJiesan) {
        this.banJiesan = banJiesan;
    }

    public boolean isDairuzongfen() {
        return dairuzongfen;
    }

    public void setDairuzongfen(boolean dairuzongfen) {
        this.dairuzongfen = dairuzongfen;
    }

    public boolean isXianshishoupai() {
        return xianshishoupai;
    }

    public void setXianshishoupai(boolean xianshishoupai) {
        this.xianshishoupai = xianshishoupai;
    }
}
