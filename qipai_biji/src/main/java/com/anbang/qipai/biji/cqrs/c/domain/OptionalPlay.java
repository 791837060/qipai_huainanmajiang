package com.anbang.qipai.biji.cqrs.c.domain;

public class OptionalPlay {

    private boolean gps;                //定位
    private boolean voice;              //语音
    private boolean qipai;              //可以弃牌
    private boolean xipaigudingfen;     //喜牌固定分
    private boolean quanhonghei;        //全黑红
    private boolean quanshun;           //全顺
    private boolean tongguan;           //通关
    private boolean sanqing;            //三清
    private boolean tonghuadatou;       //同花打头
    private boolean shunqingdatou;      //顺清打头
    private boolean shuangsantiao;      //双三条
    private boolean sizhang;            //四张
    private boolean shuangshunqing;     //双顺清
    private boolean quanshunqing;       //全顺清
    private boolean quansantiao;        //全三条
    private boolean jisumoshi;          //极速模式
    private int tuoguan;                //托管
    private boolean tuoguanjiesan;      //托管解散
    private boolean lixianchengfa;      //离线惩罚
    private double lixianchengfaScore;  //离线惩罚分数
    private int lixianshichang;         //离线时长
    private boolean jinyuanzi;          //进园子
    private int yuanzifen;              //园子分

    private int buzhunbeituichushichang;//不准备自动开始时长
    private Boolean zidongzhunbei;      //自动准备
    private Boolean zidongkaishi;       //自动开始
    private Double zidongkaishiTime;    //自动开始时间
    private boolean banVoice;           //禁止语音
    private boolean banJiesan;          //禁止解散
    private boolean dairuzongfen;       //带入总分

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

    public boolean isQuanhonghei() {
        return quanhonghei;
    }

    public void setQuanhonghei(boolean quanhonghei) {
        this.quanhonghei = quanhonghei;
    }

    public boolean isQuanshun() {
        return quanshun;
    }

    public void setQuanshun(boolean quanshun) {
        this.quanshun = quanshun;
    }

    public boolean isTongguan() {
        return tongguan;
    }

    public void setTongguan(boolean tongguan) {
        this.tongguan = tongguan;
    }

    public boolean isSanqing() {
        return sanqing;
    }

    public void setSanqing(boolean sanqing) {
        this.sanqing = sanqing;
    }

    public boolean isShunqingdatou() {
        return shunqingdatou;
    }

    public void setShunqingdatou(boolean shunqingdatou) {
        this.shunqingdatou = shunqingdatou;
    }

    public boolean isShuangsantiao() {
        return shuangsantiao;
    }

    public void setShuangsantiao(boolean shuangsantiao) {
        this.shuangsantiao = shuangsantiao;
    }

    public boolean isSizhang() {
        return sizhang;
    }

    public void setSizhang(boolean sizhang) {
        this.sizhang = sizhang;
    }

    public boolean isQuanshunqing() {
        return quanshunqing;
    }

    public void setQuanshunqing(boolean quanshunqing) {
        this.quanshunqing = quanshunqing;
    }

    public boolean isQuansantiao() {
        return quansantiao;
    }

    public void setQuansantiao(boolean quansantiao) {
        this.quansantiao = quansantiao;
    }

    public boolean isQipai() {
        return qipai;
    }

    public void setQipai(boolean qipai) {
        this.qipai = qipai;
    }

    public boolean isXipaigudingfen() {
        return xipaigudingfen;
    }

    public void setXipaigudingfen(boolean xipaigudingfen) {
        this.xipaigudingfen = xipaigudingfen;
    }

    public boolean isShuangshunqing() {
        return shuangshunqing;
    }

    public void setShuangshunqing(boolean shuangshunqing) {
        this.shuangshunqing = shuangshunqing;
    }

    public boolean isTonghuadatou() {
        return tonghuadatou;
    }

    public void setTonghuadatou(boolean tonghuadatou) {
        this.tonghuadatou = tonghuadatou;
    }

    public boolean isJisumoshi() {
        return jisumoshi;
    }

    public void setJisumoshi(boolean jisumoshi) {
        this.jisumoshi = jisumoshi;
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

    public double getLixianchengfaScore() {
        return lixianchengfaScore;
    }

    public void setLixianchengfaScore(double lixianchengfaScore) {
        this.lixianchengfaScore = lixianchengfaScore;
    }

    public int getLixianshichang() {
        return lixianshichang;
    }

    public void setLixianshichang(int lixianshichang) {
        this.lixianshichang = lixianshichang;
    }

    public int getTuoguan() {
        return tuoguan;
    }

    public void setTuoguan(int tuoguan) {
        this.tuoguan = tuoguan;
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
}
