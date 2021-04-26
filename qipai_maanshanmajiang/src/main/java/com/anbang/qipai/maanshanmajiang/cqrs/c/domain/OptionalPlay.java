package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

/**
 * 麻将可选玩法（不包括局数人数）
 */
public class OptionalPlay {
    private boolean gps;                //GPS
    private boolean voice;              //语音
    private int tuoguan;                //托管
    private boolean tuoguanjiesan;      //托管解散
    private boolean lixianchengfa;      //离线惩罚
    private int lixianshichang;         //离线惩罚时长
    private double lixianchengfaScore;  //离线惩罚分

    private int buzhunbeituichushichang;//不准备自动开始时长
    private Boolean zidongzhunbei;      //自动准备
    private Boolean zidongkaishi;       //自动开始
    private Double zidongkaishiTime;    //自动开始时间
    private boolean banVoice;           //禁止语音
    private boolean banJiesan;          //禁止解散
    private boolean dairuzongfen;       //带入总分

    private int daozi;                  //倒子
    private int suanfa;                 //算法
    private boolean zimohupai;          //自摸胡牌
    private boolean wufeng;             //无风

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

    public boolean isDairuzongfen() {
        return dairuzongfen;
    }

    public void setDairuzongfen(boolean dairuzongfen) {
        this.dairuzongfen = dairuzongfen;
    }

    public boolean isZimohupai() {
        return zimohupai;
    }

    public void setZimohupai(boolean zimohupai) {
        this.zimohupai = zimohupai;
    }

    public boolean isWufeng() {
        return wufeng;
    }

    public void setWufeng(boolean wufeng) {
        this.wufeng = wufeng;
    }

    public int getDaozi() {
        return daozi;
    }

    public void setDaozi(int daozi) {
        this.daozi = daozi;
    }

    public int getSuanfa() {
        return suanfa;
    }

    public void setSuanfa(int suanfa) {
        this.suanfa = suanfa;
    }

}
