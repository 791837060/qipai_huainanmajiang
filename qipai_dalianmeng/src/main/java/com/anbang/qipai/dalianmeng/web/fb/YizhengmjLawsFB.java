package com.anbang.qipai.dalianmeng.web.fb;

import java.util.List;

public class YizhengmjLawsFB {

    private String panshu;                      //盘数
    private String renshu;                      //人数
    private String gps = "false";               //GPS
    private String voice = "false";             //语音
    private String tuoguan = "0";               //进入托管时间
    private String jinyuanzi = "false";         //进园子
    private String budaihuangzhang = "false";   //不带黄庄
    private String daihuangzhuang = "false";    //带黄庄
    private String daisihuang = "false";        //开局带4黄庄
    private String youpeizi = "false";          //有配子
    private String shuangpeizi = "false";       //双配子
    private String quanyimen = "false";         //缺一门
    private String maima = "false";             //买码
    private String minggangchenger = "false";   //明杠乘2
    private String minggangyijiagei = "false";  //明杠一家给
    private String tuoguanjiesan = "false";     //托管解散
    private String lixianchengfa = "false";     //离线惩罚
    private String banVoice = "false";          //禁止语音
    private String banJiesan = "false";         //禁止解散
    private String dairuzongfen = "false";      //带入总分

    public YizhengmjLawsFB(List<String> lawNames) {
        lawNames.forEach((lawName) -> {
            if (lawName.equals("bj")) {// 八局
                panshu = "8";
            } else if (lawName.equals("slj")) {     //十六局
                panshu = "16";
            } else if (lawName.equals("er")) {      //二人
                renshu = "2";
            } else if (lawName.equals("sanr")) {    //三人
                renshu = "3";
            } else if (lawName.equals("sir")) {     //四人
                renshu = "4";
            } else if (lawName.equals("gps")) {     //GPS
                gps = "true";
            } else if (lawName.equals("voice")) {   //语音
                voice = "true";
            } else if (lawName.equals("btg")) {     //不托管
                tuoguan = "0";
            } else if (lawName.equals("tsw")) {     //15秒托管
                tuoguan = "15";
            } else if (lawName.equals("tss")) {     //30秒托管
                tuoguan = "30";
            } else if (lawName.equals("ssw")) {     //45秒托管
                tuoguan = "45";
            } else if (lawName.equals("tls")) {     //60秒托管
                tuoguan = "60";
            } else if (lawName.equals("tjs")) {     //90秒托管
                tuoguan = "90";
            } else if (lawName.equals("tybe")) {    //120秒托管
                tuoguan = "120";
            } else if (lawName.equals("tebs")) {    //240秒托管
                tuoguan = "240";
            } else if (lawName.equals("tsb")) {     //300秒托管
                tuoguan = "300";
            } else if (lawName.equals("jyz")) {     //进园子
                jinyuanzi = "true";
            } else if (lawName.equals("bdhz")) {    //不带黄庄
                budaihuangzhang = "true";
            } else if (lawName.equals("dhz")) {     //带黄庄
                daihuangzhuang = "true";
            } else if (lawName.equals("dsh")) {     //开局带4黄庄
                daisihuang = "true";
            } else if (lawName.equals("ypz")) {     //有配子
                youpeizi = "true";
            } else if (lawName.equals("spz")) {     //双配子
                shuangpeizi = "true";
            } else if (lawName.equals("qym")) {     //缺一门
                quanyimen = "true";
            } else if (lawName.equals("mm")) {      //买码
                maima = "true";
            } else if (lawName.equals("mgce")) {    //明杠乘2
                minggangchenger = "true";
            } else if (lawName.equals("mgyjg")) {   //明杠一家给
                minggangyijiagei = "true";
            } else if (lawName.equals("tgjs")) {    //托管解散
                tuoguanjiesan = "true";
            } else if (lawName.equals("lxcf")) {    //离线惩罚
                lixianchengfa = "true";
            } else if (lawName.equals("banVoice")) {        //禁止语音
                banVoice = "true";
            } else if (lawName.equals("banJiesan")) {       //禁止解散
                banJiesan = "true";
            } else if (lawName.equals("drzf")) {            //带入总分
                dairuzongfen = "true";
            } else {
            }
        });
    }

    public int payForCreateRoom() {
        int gold = 1;
        if (panshu.equals("16")) {
            gold = 2;
        }
        return gold;
    }

    public int payForJoinRoom() {
        int gold = 1;
        if (panshu.equals("16")) {
            gold = 2;
        }
        return gold;
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

    public String getTuoguan() {
        return tuoguan;
    }

    public void setTuoguan(String tuoguan) {
        this.tuoguan = tuoguan;
    }

    public String getJinyuanzi() {
        return jinyuanzi;
    }

    public void setJinyuanzi(String jinyuanzi) {
        this.jinyuanzi = jinyuanzi;
    }

    public String getBudaihuangzhang() {
        return budaihuangzhang;
    }

    public void setBudaihuangzhang(String budaihuangzhang) {
        this.budaihuangzhang = budaihuangzhang;
    }

    public String getDaihuangzhuang() {
        return daihuangzhuang;
    }

    public void setDaihuangzhuang(String daihuangzhuang) {
        this.daihuangzhuang = daihuangzhuang;
    }

    public String getDaisihuang() {
        return daisihuang;
    }

    public void setDaisihuang(String daisihuang) {
        this.daisihuang = daisihuang;
    }

    public String getYoupeizi() {
        return youpeizi;
    }

    public void setYoupeizi(String youpeizi) {
        this.youpeizi = youpeizi;
    }

    public String getShuangpeizi() {
        return shuangpeizi;
    }

    public void setShuangpeizi(String shuangpeizi) {
        this.shuangpeizi = shuangpeizi;
    }

    public String getQuanyimen() {
        return quanyimen;
    }

    public void setQuanyimen(String quanyimen) {
        this.quanyimen = quanyimen;
    }

    public String getMaima() {
        return maima;
    }

    public void setMaima(String maima) {
        this.maima = maima;
    }

    public String getMinggangchenger() {
        return minggangchenger;
    }

    public void setMinggangchenger(String minggangchenger) {
        this.minggangchenger = minggangchenger;
    }

    public String getMinggangyijiagei() {
        return minggangyijiagei;
    }

    public void setMinggangyijiagei(String minggangyijiagei) {
        this.minggangyijiagei = minggangyijiagei;
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
}
