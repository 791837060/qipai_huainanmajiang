package com.anbang.qipai.qinyouquan.web.fb;

import java.util.List;

public class MasmjLawsFB {

    private String panshu;
    private String renshu;
    private String gps = "false";
    private String voice = "false";
    private String tuoguan = "0";           //进入托管时间
    private String tuoguanjiesan = "false"; //托管盘结束解散
    private String lixianchengfa = "false"; //离线惩罚
    private String banVoice = "false";      //禁止语音
    private String banJiesan = "false";     //禁止解散
    private String dairuzongfen = "false";  //带入总分

    private String daozi = "false";          //倒子
    private String suanfa = "false";         //算法
    private String zimohupai = "false";      //自摸胡牌
    private String wufeng = "false";         //无风
    private String shidianqihu = "false";    //十点起胡

    public MasmjLawsFB(List<String> lawNames) {
        lawNames.forEach((lawName) -> {
            if (lawName.equals("bj")) {                 //八局
                panshu = "8";
            } else if (lawName.equals("slj")) {         //十六局
                panshu = "16";
            } else if (lawName.equals("er")) {          //二人
                renshu = "2";
            } else if (lawName.equals("sanr")) {        //三人
                renshu = "3";
            } else if (lawName.equals("sir")) {         //四人
                renshu = "4";
            } else if (lawName.equals("gps")) {         //gps
                gps = "true";
            } else if (lawName.equals("voice")) {       //voice
                voice = "true";
            } else if (lawName.equals("btg")) {         //不托管
                tuoguan = "0";
            } else if (lawName.equals("tsw")) {         //15秒后进入托管
                tuoguan = "15";
            } else if (lawName.equals("tss")) {         //30秒后进入托管
                tuoguan = "30";
            } else if (lawName.equals("ssw")) {         //45秒托管
                tuoguan = "45";
            } else if (lawName.equals("tls")) {         //60秒后进入托管
                tuoguan = "60";
            } else if (lawName.equals("tjs")) {         //90秒后进入托管
                tuoguan = "90";
            } else if (lawName.equals("tybe")) {        //120秒后进入托管
                tuoguan = "120";
            } else if (lawName.equals("tebs")) {        //240秒后进入托管
                tuoguan = "240";
            } else if (lawName.equals("tsb")) {         //300秒后进入托管
                tuoguan = "300";
            } else if (lawName.equals("tgjs")) {        //托管解散
                tuoguanjiesan = "true";
            } else if (lawName.equals("lxcf")) {        //离线惩罚
                lixianchengfa = "true";
            } else if (lawName.equals("banVoice")) {    //禁止语音
                banVoice = "true";
            } else if (lawName.equals("banJiesan")) {   //禁止解散
                banJiesan = "true";
            } else if (lawName.equals("drzf")) {        //带入总分
                dairuzongfen = "true";
            } else if (lawName.equals("yd")) {          //倒子 1倒
                daozi = "1";
            } else if (lawName.equals("ed")) {          //倒子 2倒
                daozi = "2";
            } else if (lawName.equals("sd")) {          //倒子 3倒
                daozi = "3";
            } else if (lawName.equals("shd")) {         //算法 10倒
                suanfa = "10";
            } else if (lawName.equals("shwd")) {        //算法 15倒
                suanfa = "15";
            } else if (lawName.equals("eswd")) {        //算法 25倒
                suanfa = "25";
            } else if (lawName.equals("wsd")) {         //算法 50倒
                suanfa = "50";
            } else if (lawName.equals("zmhp")) {        //自摸胡牌
                zimohupai = "true";
            } else if (lawName.equals("wf")) {          //无风
                wufeng = "true";
            } else if (lawName.equals("sdqh")) {        //十点起胡
                shidianqihu = "true";
            } else {
            }
        });
    }


    public int payForCreateRoom() {
        int gold = 15;
        switch (daozi) {
            case "1":
                gold = 6;
                break;
            case "2":
                gold = 10;
                break;
            case "3":
                gold = 15;
                break;
        }
        return gold;
    }

    public int payForJoinRoom() {
        int gold = 15;
        switch (daozi) {
            case "1":
                gold = 6;
                break;
            case "2":
                gold = 10;
                break;
            case "3":
                gold = 15;
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

    public String getDaozi() {
        return daozi;
    }

    public void setDaozi(String daozi) {
        this.daozi = daozi;
    }

    public String getSuanfa() {
        return suanfa;
    }

    public void setSuanfa(String suanfa) {
        this.suanfa = suanfa;
    }

    public String getZimohupai() {
        return zimohupai;
    }

    public void setZimohupai(String zimohupai) {
        this.zimohupai = zimohupai;
    }

    public String getWufeng() {
        return wufeng;
    }

    public void setWufeng(String wufeng) {
        this.wufeng = wufeng;
    }

    public String getShidianqihu() {
        return shidianqihu;
    }

    public void setShidianqihu(String shidianqihu) {
        this.shidianqihu = shidianqihu;
    }
}
