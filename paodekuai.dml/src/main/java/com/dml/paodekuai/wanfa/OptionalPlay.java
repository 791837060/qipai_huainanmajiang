package com.dml.paodekuai.wanfa;

/**
 * @Description: 跑的快可选玩法（不包括局数人数）
 */
public class OptionalPlay {
    private boolean bichu;              //黑桃三先出必出
    private boolean hongxinsanxianchu;  //红桃三先出必出

    private boolean heitaosanXianchuBubichu;//黑桃三先出不必出
    private boolean hongtaosanXianchuBubichu;//红心三先出不必出

    private boolean biya;               //能压牌必须压
    private boolean sandaique;          //尾牌时三带可缺牌
    private boolean feijique;           //尾牌时飞机可缺牌
    private boolean showShoupaiNum;     //显示剩余手牌
    private boolean zhuaniao;           //红桃10抓鸟玩法
    private boolean sidaier;            //可四带二
    private boolean sidaisan;           //可四带三
    private boolean shiwuzhang;         //十五张牌
    private boolean shiliuzhang;        //十六张牌
    private boolean zhadanwufen;        //炸弹五分
    private boolean zhadanshifen;       //炸弹十分
    private boolean sanAJiaYiBoom;      //3个A+1炸
    private boolean shouABi2;           //首A必2压
    private boolean A2Xiafang;          //A2下放
    private boolean zhadanfanbei;       //炸弹翻倍
    private boolean yingsuanzha;        //赢了算炸
    private boolean zhadanbeiyabugeifen;//炸弹被压不给分
    private boolean daxiaoguan;         //大小关
    private boolean fanguan;            //反关
    private boolean sandailiangdan;     //三带两单
    private boolean san3JiaYiBoom;      //3个3+1炸
    private boolean sidaiyiBoom;        //四带一炸弹
    private boolean xiaoguanjiufen;     //小关加9分

    private boolean gps;                //定位
    private boolean voice;              //语音
    private int tuoguan;                //进入托管时间
    private boolean tuoguanjiesan;      //托管解散
    private boolean lixianchengfa;      //离线惩罚
    private double lixianchengfaScore;  //离线惩罚分数
    private int lixianshichang;         //离线时长

    private int buzhunbeituichushichang;//不准备自动退出时长
    private Boolean zidongzhunbei;      //自动准备
    private Boolean zidongkaishi;       //自动开始
    private Double zidongkaishiTime;    //自动开始时间
    private boolean banVoice;           //禁止语音
    private boolean banJiesan;          //禁止解散
    private boolean dairuzongfen;       //带入总分

    private boolean jinyuanzi;          //进园子
    private int yuanzifen;              //园子分

    /**
     * 检查玩法，二人玩随机出牌
     */
    public boolean checkPlay(int playerNum) {
        if (playerNum == 2 && bichu) {
            return false;
        }
        return true;
    }

    public OptionalPlay() {
    }


    public boolean isBichu() {
        return bichu;
    }

    public void setBichu(boolean bichu) {
        this.bichu = bichu;
    }

    public boolean isBiya() {
        return biya;
    }

    public void setBiya(boolean biya) {
        this.biya = biya;
    }

    public boolean isSandaique() {
        return sandaique;
    }

    public void setSandaique(boolean sandaique) {
        this.sandaique = sandaique;
    }

    public boolean isFeijique() {
        return feijique;
    }

    public void setFeijique(boolean feijique) {
        this.feijique = feijique;
    }

    public boolean isShowShoupaiNum() {
        return showShoupaiNum;
    }

    public void setShowShoupaiNum(boolean showShoupaiNum) {
        this.showShoupaiNum = showShoupaiNum;
    }

    public boolean isZhuaniao() {
        return zhuaniao;
    }

    public void setZhuaniao(boolean zhuaniao) {
        this.zhuaniao = zhuaniao;
    }

    public boolean isSidaier() {
        return sidaier;
    }

    public void setSidaier(boolean sidaier) {
        this.sidaier = sidaier;
    }

    public boolean isSidaisan() {
        return sidaisan;
    }

    public void setSidaisan(boolean sidaisan) {
        this.sidaisan = sidaisan;
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

    public boolean isZhadanshifen() {
        return zhadanshifen;
    }

    public void setZhadanshifen(boolean zhadanshifen) {
        this.zhadanshifen = zhadanshifen;
    }

    public boolean isShiwuzhang() {
        return shiwuzhang;
    }

    public void setShiwuzhang(boolean shiwuzhang) {
        this.shiwuzhang = shiwuzhang;
    }

    public boolean isShiliuzhang() {
        return shiliuzhang;
    }

    public void setShiliuzhang(boolean shiliuzhang) {
        this.shiliuzhang = shiliuzhang;
    }

    public boolean isZhadanfanbei() {
        return zhadanfanbei;
    }

    public void setZhadanfanbei(boolean zhadanfanbei) {
        this.zhadanfanbei = zhadanfanbei;
    }

    public boolean isSanAJiaYiBoom() {
        return sanAJiaYiBoom;
    }

    public void setSanAJiaYiBoom(boolean sanAJiaYiBoom) {
        this.sanAJiaYiBoom = sanAJiaYiBoom;
    }

    public boolean isShouABi2() {
        return shouABi2;
    }

    public void setShouABi2(boolean shouABi2) {
        this.shouABi2 = shouABi2;
    }

    public boolean isA2Xiafang() {
        return A2Xiafang;
    }

    public void setA2Xiafang(boolean a2Xiafang) {
        A2Xiafang = a2Xiafang;
    }

    public boolean isYingsuanzha() {
        return yingsuanzha;
    }

    public void setYingsuanzha(boolean yingsuanzha) {
        this.yingsuanzha = yingsuanzha;
    }

    public boolean isZhadanbeiyabugeifen() {
        return zhadanbeiyabugeifen;
    }

    public void setZhadanbeiyabugeifen(boolean zhadanbeiyabugeifen) {
        this.zhadanbeiyabugeifen = zhadanbeiyabugeifen;
    }

    public boolean isFanguan() {
        return fanguan;
    }

    public void setFanguan(boolean fanguan) {
        this.fanguan = fanguan;
    }

    public boolean isDaxiaoguan() {
        return daxiaoguan;
    }

    public void setDaxiaoguan(boolean daxiaoguan) {
        this.daxiaoguan = daxiaoguan;
    }

    public boolean isSandailiangdan() {
        return sandailiangdan;
    }

    public void setSandailiangdan(boolean sandailiangdan) {
        this.sandailiangdan = sandailiangdan;
    }

    public boolean isZhadanwufen() {
        return zhadanwufen;
    }

    public void setZhadanwufen(boolean zhadanwufen) {
        this.zhadanwufen = zhadanwufen;
    }

    public boolean isHongxinsanxianchu() {
        return hongxinsanxianchu;
    }

    public void setHongxinsanxianchu(boolean hongxinsanxianchu) {
        this.hongxinsanxianchu = hongxinsanxianchu;
    }

    public boolean isSan3JiaYiBoom() {
        return san3JiaYiBoom;
    }

    public void setSan3JiaYiBoom(boolean san3JiaYiBoom) {
        this.san3JiaYiBoom = san3JiaYiBoom;
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

    public boolean isSidaiyiBoom() {
        return sidaiyiBoom;
    }

    public void setSidaiyiBoom(boolean sidaiyiBoom) {
        this.sidaiyiBoom = sidaiyiBoom;
    }

    public boolean isHeitaosanXianchuBubichu() {
        return heitaosanXianchuBubichu;
    }

    public void setHeitaosanXianchuBubichu(boolean heitaosanXianchuBubichu) {
        this.heitaosanXianchuBubichu = heitaosanXianchuBubichu;
    }

    public boolean isHongtaosanXianchuBubichu() {
        return hongtaosanXianchuBubichu;
    }

    public void setHongtaosanXianchuBubichu(boolean hongtaosanXianchuBubichu) {
        this.hongtaosanXianchuBubichu = hongtaosanXianchuBubichu;
    }

    public boolean isXiaoguanjiufen() {
        return xiaoguanjiufen;
    }

    public void setXiaoguanjiufen(boolean xiaoguanjiufen) {
        this.xiaoguanjiufen = xiaoguanjiufen;
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
