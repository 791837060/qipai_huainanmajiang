package com.anbang.qipai.game.web.fb;

import java.util.List;

public class YzmjLawsFB {

    private String panshu;
    private String renshu;
    private String gps = "false";
    private String voice = "false";
    private String yitiaolongliufen = "false";  //一条龙6分
    private String sipeihusibei = "false";      //四配胡四倍
    private String budaibanzihu = "false";      //不带搬子胡
    private String peiziwanfa = "false";        //配子玩法
    private String qidui = "false";             //七对
    private String fengyise = "false";          //风一色
    private String dihu = "false";              //地胡
    private String jinyuanzi = "false";         //进园子
    private String tuoguan = "0";               //进入托管时间
    private String tuoguanjiesan = "false";     //托管解散
    private String lixianchengfa = "false";     //离线惩罚
    private String banVoice = "false";          //禁止语音
    private String banJiesan="false";           //禁止解散

    public YzmjLawsFB(List<String> lawNames) {
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
            } else if (lawName.equals("ytllf")) {   //一条龙6分
                yitiaolongliufen = "true";
            } else if (lawName.equals("sphsb")) {   //四配胡四倍
                sipeihusibei = "true";
            } else if (lawName.equals("bdbzh")) {   //不带搬子胡
                budaibanzihu = "true";
            } else if (lawName.equals("pzwf")) {    //配子玩法
                peiziwanfa = "true";
            } else if (lawName.equals("qd")) {      //七对
                qidui = "true";
            } else if (lawName.equals("fys")) {     //风一色
                fengyise = "true";
            } else if (lawName.equals("dh")) {      //地胡
                dihu = "true";
            } else if (lawName.equals("jyz")) {     //进园子
                jinyuanzi = "true";
            } else if (lawName.equals("tgjs")) {    //托管解散
                tuoguanjiesan = "true";
            } else if (lawName.equals("lxcf")) {    //离线惩罚
                lixianchengfa = "true";
            } else if (lawName.equals("banVoice")) {//
                banVoice = "true";
            } else if (lawName.equals("banJiesan")) {//
                banJiesan = "true";
            } else {
            }
        });
    }


    public int payForCreateRoom() {
        int gold = 1;
        switch (panshu) {
            case "4":
                gold = 2;
                break;
            case "8":
                gold = 4;
                break;
            case "16":
                gold = 8;
                break;
        }
        return gold;
    }

    public int payForJoinRoom() {
        int gold = 1;
        switch (panshu) {
            case "4":
                gold = 2;
                break;
            case "8":
                gold = 4;
                break;
            case "16":
                gold = 8;
                break;
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

    public String getYitiaolongliufen() {
        return yitiaolongliufen;
    }

    public void setYitiaolongliufen(String yitiaolongliufen) {
        this.yitiaolongliufen = yitiaolongliufen;
    }

    public String getSipeihusibei() {
        return sipeihusibei;
    }

    public void setSipeihusibei(String sipeihusibei) {
        this.sipeihusibei = sipeihusibei;
    }

    public String getBudaibanzihu() {
        return budaibanzihu;
    }

    public void setBudaibanzihu(String budaibanzihu) {
        this.budaibanzihu = budaibanzihu;
    }

    public String getPeiziwanfa() {
        return peiziwanfa;
    }

    public void setPeiziwanfa(String peiziwanfa) {
        this.peiziwanfa = peiziwanfa;
    }

    public String getQidui() {
        return qidui;
    }

    public void setQidui(String qidui) {
        this.qidui = qidui;
    }

    public String getFengyise() {
        return fengyise;
    }

    public void setFengyise(String fengyise) {
        this.fengyise = fengyise;
    }

    public String getDihu() {
        return dihu;
    }

    public void setDihu(String dihu) {
        this.dihu = dihu;
    }

    public String getJinyuanzi() {
        return jinyuanzi;
    }

    public void setJinyuanzi(String jinyuanzi) {
        this.jinyuanzi = jinyuanzi;
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
}
