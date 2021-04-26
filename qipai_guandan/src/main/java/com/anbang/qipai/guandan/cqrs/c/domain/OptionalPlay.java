package com.anbang.qipai.guandan.cqrs.c.domain;

import com.anbang.qipai.guandan.cqrs.c.domain.wanfa.ChaPai;
import com.anbang.qipai.guandan.cqrs.c.domain.wanfa.FaPai;
import com.dml.shuangkou.wanfa.BianXingWanFa;

/**
 * 可选玩法
 */
public class OptionalPlay {
    private BianXingWanFa bx;

    private boolean gps;                    //解散
    private boolean voice;                  //语音
    private int tuoguan;                    //进入托管时间
    private boolean tuoguanjiesan;          //盘打完解散
    private boolean lixianchengfa;          //离线惩罚
    private int lixianshichang;             //离线时长
    private double lixianchengfaScore;      //离线惩罚分数
    private int buzhunbeituichushichang;    //不准备退出时长
    private Boolean zidongzhunbei;          //自动准备
    private Boolean zidongkaishi;           //自动开始
    private Double zidongkaishiTime;        //自动开始时长
    private boolean banVoice;               //禁止语音
    private boolean banJiesan;              //禁止解散
    private boolean dairuzongfen;           //带入总分
    private boolean jinyuanzi;              //进园子
    private int yuanzifen;                  //园子分

    public OptionalPlay() {
    }

    public OptionalPlay(BianXingWanFa bx, boolean gps, boolean voice, int tuoguan, boolean tuoguanjiesan, boolean lixianchengfa, int lixianshichang, double lixianchengfaScore,
                        int buzhunbeituichushichang, Boolean zidongzhunbei, Boolean zidongkaishi, Double zidongkaishiTime, boolean banVoice, boolean banJiesan, boolean dairuzongfen) {
        this.bx = bx;
        this.gps = gps;
        this.voice = voice;
        this.tuoguan = tuoguan;
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
        this.dairuzongfen = dairuzongfen;
    }

    public BianXingWanFa getBx() {
        return bx;
    }

    public void setBx(BianXingWanFa bx) {
        this.bx = bx;
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

    public boolean isDairuzongfen() {
        return dairuzongfen;
    }

    public void setDairuzongfen(boolean dairuzongfen) {
        this.dairuzongfen = dairuzongfen;
    }

    public boolean isJinyuanzi() {
        return jinyuanzi;
    }

    public void setJinyuanzi(boolean jinyuanzi) {
        this.jinyuanzi = jinyuanzi;
    }

    public int getYuanzifen() {
        return yuanzifen;
    }

    public void setYuanzifen(int yuanzifen) {
        this.yuanzifen = yuanzifen;
    }
}
