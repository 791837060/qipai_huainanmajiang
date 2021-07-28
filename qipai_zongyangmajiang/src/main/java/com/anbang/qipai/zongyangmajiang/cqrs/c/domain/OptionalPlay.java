package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

/**
 * @Description: 麻将可选玩法（不包括局数人数）
 */
public class OptionalPlay {
    /**
     * GPS
     */
    private boolean gps;
    /**
     * 语音
     */
    private boolean voice;

    /**
     * 可碰
     */
    private boolean kepeng;

    private int tuoguan;                    //进入托管时间
    private boolean tuoguanjiesan;          //盘打完解散
    private boolean lixianchengfa;          //离线惩罚
    private int lixianshichang;             //离线时长
    private double lixianchengfaScore;      //离线惩罚分数
    private int buzhunbeituichushichang;    //不准备退出时长
    private Boolean zidongzhunbei;          //自动准备
    private Boolean zidongkaishi;           //自动开始
    private Double zidongkaishiTime;        //自动开始时间
    private boolean banVoice;               //禁止语音
    private boolean banJiesan;              //禁止解散

    public OptionalPlay() {
    }

    public OptionalPlay(boolean kepeng,boolean gps, boolean voice, int tuoguan, boolean tuoguanjiesan, boolean lixianchengfa, int lixianshichang, double lixianchengfaScore, int buzhunbeituichushichang, Boolean zidongzhunbei, Boolean zidongkaishi, Double zidongkaishiTime, boolean banVoice, boolean banJiesan) {
        this.gps = gps;
        this.voice = voice;
        this.tuoguan = tuoguan;
        this.kepeng = kepeng;
        this.tuoguanjiesan = tuoguanjiesan;
        this.lixianchengfa = lixianchengfa;
        this.lixianshichang = lixianshichang;
        this.lixianchengfaScore = lixianchengfaScore;
        this.buzhunbeituichushichang = buzhunbeituichushichang;
        this.zidongzhunbei = zidongzhunbei;
        this.zidongkaishi = zidongkaishi;
        this.zidongkaishiTime = zidongkaishiTime;
        this.banVoice = banVoice;
        this.banJiesan = banJiesan;
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
    public int getTuoguan() {
        return tuoguan;
    }

    public void setTuoguan(int tuoguan) {
        this.tuoguan = tuoguan;
    }

    public boolean isTuoguanjiesan() {
        return tuoguanjiesan;
    }

    public void setTuoguanjiesan(boolean tuoguanjiesan) {
        this.tuoguanjiesan = tuoguanjiesan;
    }

    public boolean isLixianchengfa() {
        return lixianchengfa;
    }

    public void setLixianchengfa(boolean lixianchengfa) {
        this.lixianchengfa = lixianchengfa;
    }

    public int getLixianshichang() {
        return lixianshichang;
    }

    public void setLixianshichang(int lixianshichang) {
        this.lixianshichang = lixianshichang;
    }

    public double getLixianchengfaScore() {
        return lixianchengfaScore;
    }

    public void setLixianchengfaScore(double lixianchengfaScore) {
        this.lixianchengfaScore = lixianchengfaScore;
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

    public boolean isKepeng() {
        return kepeng;
    }

    public void setKepeng(boolean kepeng) {
        this.kepeng = kepeng;
    }
}
