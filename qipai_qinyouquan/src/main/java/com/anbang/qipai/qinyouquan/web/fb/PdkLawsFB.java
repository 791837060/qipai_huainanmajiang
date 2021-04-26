package com.anbang.qipai.qinyouquan.web.fb;

import java.util.List;

public class PdkLawsFB {
    private String panshu;                       //盘数
    private String renshu;                       //人数
    private String bichu = "false";              //黑桃三先出必出
    private String hongxinsanxianchu = "false";  //红桃三先出必出

    private String heitaosanXianchuBubichu = "false";//黑桃三先出不必出
    private String hongtaosanXianchuBubichu = "false";//红心三先出不必出

    private String biya = "false";               //能压牌必须压
    private String sandaique = "false";          //尾牌时三带可缺牌
    private String feijique = "false";           //尾牌时飞机可缺牌
    private String showShoupaiNum = "false";     //显示剩余手牌
    private String zhuaniao = "false";           //红桃10抓鸟玩法
    private String sandailiangdan = "false";     //三带两单
    private String sidaier = "false";            //可四带二
    private String sidaisan = "false";           //可四带三
    private String shiwuzhang = "false";         //十五张牌
    private String shiliuzhang = "false";        //十六张牌
    private String shouABi2 = "false";           //首张A必须用2压
    private String A2Xiafang = "false";          //A2下放
    private String daxiaoguan = "false";         //大小关
    private String fanguan = "false";            //反关
    private String aBoom = "false";              //三A算炸
    private String sanAJiaYiBoom = "false";      //3个A加1炸
    private String san3JiaYiBoom = "false";      //3个3加1炸
    private String sidaiyiBoom = "false";        //四带一炸弹
    private String zhadanwufen = "false";        //炸弹五分
    private String zhadanshifen = "false";       //炸弹十分
    private String zhadanfanbei = "false";       //炸弹分翻倍
    private String yingsuanzha = "false";        //赢了算炸
    private String zhadanbeiyabugeifen = "false";//炸弹被压不算分
    private String xiaoguanjiufen = "false";      //小关加9分

    private String gps = "false";                //GPS
    private String voice = "false";              //语音
    private String tuoguan = "0";                //进入托管时间
    private String tuoguanjiesan = "false";      //托管解散
    private String lixianchengfa = "false";      //离线惩罚
    private String banVoice = "false";           //禁止语音
    private String banJiesan = "false";          //禁止解散
    private String dairuzongfen = "false";       //带入总分
    private String jinyuanzi = "false";          //进园子

    public PdkLawsFB() {
    }

    public PdkLawsFB(List<String> lawNames) {
        lawNames.forEach((lawName) -> {
            if (lawName.equals("lj")) {                     //6局
                panshu = "6";
            } else if (lawName.equals("bj")) {              //8局
                panshu = "8";
            } else if (lawName.equals("shj")) {             //10局
                panshu = "10";
            } else if (lawName.equals("esj")) {             //20局
                panshu = "20";
            } else if (lawName.equals("er")) {              //二人
                renshu = "2";
            } else if (lawName.equals("sanr")) {            //三人
                renshu = "3";
            } else if (lawName.equals("bichu")) {
                bichu = "true";
            } else if (lawName.equals("biya")) {
                biya = "true";
            } else if (lawName.equals("aBoom")) {
                aBoom = "true";
            } else if (lawName.equals("sandaique")) {
                sandaique = "true";
            } else if (lawName.equals("feijique")) {
                feijique = "true";
            } else if (lawName.equals("showShoupaiNum")) {  //
                showShoupaiNum = "true";
            } else if (lawName.equals("zhuaniao")) {        //
                zhuaniao = "true";
            } else if (lawName.equals("sidaier")) {         //
                sidaier = "true";
            } else if (lawName.equals("sidaisan")) {        //
                sidaisan = "true";
            } else if (lawName.equals("shiwuzhang")) {      //
                shiwuzhang = "true";
            } else if (lawName.equals("shiliuzhang")) {     //
                shiliuzhang = "true";
            } else if (lawName.equals("btg")) {             //不托管
                tuoguan = "0";
            } else if (lawName.equals("tsw")) {             //15秒进入托管
                tuoguan = "15";
            } else if (lawName.equals("tss")) {             //30秒进入托管
                tuoguan = "30";
            } else if (lawName.equals("ssw")) {             //45秒托管
                tuoguan = "45";
            } else if (lawName.equals("tls")) {             //60秒进入托管
                tuoguan = "60";
            } else if (lawName.equals("tjs")) {             //90秒进入托管
                tuoguan = "90";
            } else if (lawName.equals("tybe")) {            //120秒进入托管
                tuoguan = "120";
            } else if (lawName.equals("tebs")) {            //240秒进入托管
                tuoguan = "240";
            } else if (lawName.equals("tsb")) {             //300秒进入托管
                tuoguan = "300";
            } else if (lawName.equals("gps")) {
                gps = "true";
            } else if (lawName.equals("voice")) {
                voice = "true";
            } else if (lawName.equals("zdwf")) {            //炸弹5分
                zhadanwufen = "true";
            } else if (lawName.equals("zdsf")) {            //炸弹10分
                zhadanshifen = "true";
            } else if (lawName.equals("sajyb")) {           //3A加1炸
                sanAJiaYiBoom = "true";
            } else if (lawName.equals("sabe")) {            //首张A必须用2压
                shouABi2 = "true";
            } else if (lawName.equals("aexf")) {            //A2下放
                A2Xiafang = "true";
            } else if (lawName.equals("zdfb")) {            //炸弹分翻倍
                zhadanfanbei = "true";
            } else if (lawName.equals("ysz")) {             //赢了算炸
                yingsuanzha = "true";
            } else if (lawName.equals("zdbybgf")) {         //炸弹被压不算分
                zhadanbeiyabugeifen = "true";
            } else if (lawName.equals("dxg")) {             //大小关
                daxiaoguan = "true";
            } else if (lawName.equals("fg")) {              //反关
                fanguan = "true";
            } else if (lawName.equals("sdld")) {            //三大两单
                sandailiangdan = "true";
            } else if (lawName.equals("bichuhxs")) {        //红心三先出
                hongxinsanxianchu = "true";
            } else if (lawName.equals("ssjyb")) {           //3个加1炸
                san3JiaYiBoom = "true";
            } else if (lawName.equals("tgjs")) {            //托管解散
                tuoguanjiesan = "true";
            } else if (lawName.equals("lxcf")) {            //离线惩罚
                lixianchengfa = "true";
            } else if (lawName.equals("banVoice")) {        //禁止语音
                banVoice = "true";
            } else if (lawName.equals("banJiesan")) {       //禁止解散
                banJiesan = "true";
            } else if (lawName.equals("drzf")) {            //带入总分
                dairuzongfen = "true";
            } else if (lawName.equals("sdyb")) {            //四带一算炸弹
                sidaiyiBoom = "true";
            } else if (lawName.equals("heitsxcbbc")) {      //黑桃三先出不必出
                heitaosanXianchuBubichu = "true";
            } else if (lawName.equals("hongtsxcbbc")) {     //红桃三先出不必出
                hongtaosanXianchuBubichu = "true";
            } else if (lawName.equals("xgjf")) {            //小关9分
                xiaoguanjiufen = "true";
            } else if (lawName.equals("jyz")) {             //进园子
                jinyuanzi = "true";
            } else {

            }
        });
    }

    public int payForCreateRoom() {
        int gold = 1;
        switch (panshu) {
            case "6":
                gold = 3;
                break;
            case "8":
                gold = 3;
                break;
            case "10":
                gold = 4;
                break;
            case "20":
                gold = 8;
                break;
        }
        return gold;
    }

    public int payForJoinRoom() {
        return 1;
    }

    public String getPanshu() {
        return panshu;
    }

    public void setPanshu(String panshu) {
        this.panshu = panshu;
    }

    public String getRenshu() {
        return renshu;
    }

    public void setRenshu(String renshu) {
        this.renshu = renshu;
    }

    public String getBichu() {
        return bichu;
    }

    public void setBichu(String bichu) {
        this.bichu = bichu;
    }

    public String getBiya() {
        return biya;
    }

    public void setBiya(String biya) {
        this.biya = biya;
    }

    public String getaBoom() {
        return aBoom;
    }

    public void setaBoom(String aBoom) {
        this.aBoom = aBoom;
    }

    public String getSandaique() {
        return sandaique;
    }

    public void setSandaique(String sandaique) {
        this.sandaique = sandaique;
    }

    public String getFeijique() {
        return feijique;
    }

    public void setFeijique(String feijique) {
        this.feijique = feijique;
    }

    public String getShowShoupaiNum() {
        return showShoupaiNum;
    }

    public void setShowShoupaiNum(String showShoupaiNum) {
        this.showShoupaiNum = showShoupaiNum;
    }

    public String getZhuaniao() {
        return zhuaniao;
    }

    public void setZhuaniao(String zhuaniao) {
        this.zhuaniao = zhuaniao;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getSidaier() {
        return sidaier;
    }

    public void setSidaier(String sidaier) {
        this.sidaier = sidaier;
    }

    public String getSidaisan() {
        return sidaisan;
    }

    public void setSidaisan(String sidaisan) {
        this.sidaisan = sidaisan;
    }

    public String getTuoguan() {
        return tuoguan;
    }

    public void setTuoguan(String tuoguan) {
        this.tuoguan = tuoguan;
    }

    public String getZhadanshifen() {
        return zhadanshifen;
    }

    public void setZhadanshifen(String zhadanshifen) {
        this.zhadanshifen = zhadanshifen;
    }

    public String getShiwuzhang() {
        return shiwuzhang;
    }

    public void setShiwuzhang(String shiwuzhang) {
        this.shiwuzhang = shiwuzhang;
    }

    public String getShiliuzhang() {
        return shiliuzhang;
    }

    public void setShiliuzhang(String shiliuzhang) {
        this.shiliuzhang = shiliuzhang;
    }

    public String getZhadanwufen() {
        return zhadanwufen;
    }

    public void setZhadanwufen(String zhadanwufen) {
        this.zhadanwufen = zhadanwufen;
    }

    public String getSanAJiaYiBoom() {
        return sanAJiaYiBoom;
    }

    public void setSanAJiaYiBoom(String sanAJiaYiBoom) {
        this.sanAJiaYiBoom = sanAJiaYiBoom;
    }

    public String getShouABi2() {
        return shouABi2;
    }

    public void setShouABi2(String shouABi2) {
        this.shouABi2 = shouABi2;
    }

    public String getA2Xiafang() {
        return A2Xiafang;
    }

    public void setA2Xiafang(String a2Xiafang) {
        A2Xiafang = a2Xiafang;
    }

    public String getZhadanfanbei() {
        return zhadanfanbei;
    }

    public void setZhadanfanbei(String zhadanfanbei) {
        this.zhadanfanbei = zhadanfanbei;
    }

    public String getYingsuanzha() {
        return yingsuanzha;
    }

    public void setYingsuanzha(String yingsuanzha) {
        this.yingsuanzha = yingsuanzha;
    }

    public String getZhadanbeiyabugeifen() {
        return zhadanbeiyabugeifen;
    }

    public void setZhadanbeiyabugeifen(String zhadanbeiyabugeifen) {
        this.zhadanbeiyabugeifen = zhadanbeiyabugeifen;
    }

    public String getDaxiaoguan() {
        return daxiaoguan;
    }

    public void setDaxiaoguan(String daxiaoguan) {
        this.daxiaoguan = daxiaoguan;
    }

    public String getFanguan() {
        return fanguan;
    }

    public void setFanguan(String fanguan) {
        this.fanguan = fanguan;
    }

    public String getSandailiangdan() {
        return sandailiangdan;
    }

    public void setSandailiangdan(String sandailiangdan) {
        this.sandailiangdan = sandailiangdan;
    }

    public String getHongxinsanxianchu() {
        return hongxinsanxianchu;
    }

    public void setHongxinsanxianchu(String hongxinsanxianchu) {
        this.hongxinsanxianchu = hongxinsanxianchu;
    }

    public String getSan3JiaYiBoom() {
        return san3JiaYiBoom;
    }

    public void setSan3JiaYiBoom(String san3JiaYiBoom) {
        this.san3JiaYiBoom = san3JiaYiBoom;
    }

    public String getTuoguanjiesan() {
        return tuoguanjiesan;
    }

    public void setTuoguanjiesan(String tuoguanjiesan) {
        this.tuoguanjiesan = tuoguanjiesan;
    }

    public String getLixianchengfa() {
        return lixianchengfa;
    }

    public void setLixianchengfa(String lixianchengfa) {
        this.lixianchengfa = lixianchengfa;
    }

    public String getBanVoice() {
        return banVoice;
    }

    public void setBanVoice(String banVoice) {
        this.banVoice = banVoice;
    }

    public String getBanJiesan() {
        return banJiesan;
    }

    public void setBanJiesan(String banJiesan) {
        this.banJiesan = banJiesan;
    }

    public String getDairuzongfen() {
        return dairuzongfen;
    }

    public void setDairuzongfen(String dairuzongfen) {
        this.dairuzongfen = dairuzongfen;
    }

    public String getSidaiyiBoom() {
        return sidaiyiBoom;
    }

    public void setSidaiyiBoom(String sidaiyiBoom) {
        this.sidaiyiBoom = sidaiyiBoom;
    }

    public String getHeitaosanXianchuBubichu() {
        return heitaosanXianchuBubichu;
    }

    public void setHeitaosanXianchuBubichu(String heitaosanXianchuBubichu) {
        this.heitaosanXianchuBubichu = heitaosanXianchuBubichu;
    }

    public String getHongtaosanXianchuBubichu() {
        return hongtaosanXianchuBubichu;
    }

    public void setHongtaosanXianchuBubichu(String hongtaosanXianchuBubichu) {
        this.hongtaosanXianchuBubichu = hongtaosanXianchuBubichu;
    }

    public String getXiaoguanjiufen() {
        return xiaoguanjiufen;
    }

    public void setXiaoguanjiufen(String xiaoguanjiufen) {
        this.xiaoguanjiufen = xiaoguanjiufen;
    }

    public String getJinyuanzi() {
        return jinyuanzi;
    }

    public void setJinyuanzi(String jinyuanzi) {
        this.jinyuanzi = jinyuanzi;
    }
}
