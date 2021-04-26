package com.anbang.qipai.qinyouquan.web.fb;

import java.util.List;

public class GymjLawsFB {

    private String panshu;                      //盘数
    private String renshu;                      //人数
    private String gps = "false";               //GPS
    private String voice = "false";             //语音
    private String tuoguan = "0";               //进入托管时间
    private String jinyuanzi = "false";         //进园子
    private String tuoguanjiesan = "false";     //托管解散
    private String lixianchengfa = "false";     //离线惩罚
    private String banVoice = "false";          //禁止语音
    private String banJiesan = "false";         //禁止解散
    private String dairuzongfen = "false";      //带入总分

    private String babashuang = "false";        //把把双
    private String baosanzui = "false";         //包三嘴
    private String hupaitishi = "false";        //胡牌提示

    public GymjLawsFB(List<String> lawNames) {
        lawNames.forEach((lawName) -> {
            if (lawName.equals("bj")) {                 //八局
                panshu = "8";
            } else if (lawName.equals("slj")) {         //十六局
                panshu = "16";
            } else if (lawName.equals("sj")) {          //四局
                panshu = "4";
            } else if (lawName.equals("er")) {          //二人
                renshu = "2";
            } else if (lawName.equals("sanr")) {        //三人
                renshu = "3";
            } else if (lawName.equals("sir")) {         //四人
                renshu = "4";
            } else if (lawName.equals("gps")) {         //GPS
                gps = "true";
            } else if (lawName.equals("voice")) {       //语音
                voice = "true";
            } else if (lawName.equals("btg")) {         //不托管
                tuoguan = "0";
            } else if (lawName.equals("tsw")) {         //15秒托管
                tuoguan = "15";
            } else if (lawName.equals("tss")) {         //30秒托管
                tuoguan = "30";
            } else if (lawName.equals("ssw")) {         //45秒托管
                tuoguan = "45";
            } else if (lawName.equals("tls")) {         //60秒托管
                tuoguan = "60";
            } else if (lawName.equals("tjs")) {         //90秒托管
                tuoguan = "90";
            } else if (lawName.equals("tybe")) {        //120秒托管
                tuoguan = "120";
            } else if (lawName.equals("tebs")) {        //240秒托管
                tuoguan = "240";
            } else if (lawName.equals("tsb")) {         //300秒托管
                tuoguan = "300";
            } else if (lawName.equals("jyz")) {         //进园子
                jinyuanzi = "true";
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
            } else if (lawName.equals("bbs")) {         //把把双
                babashuang = "true";
            } else if (lawName.equals("bsz")) {         //包三嘴
                baosanzui = "true";
            } else if (lawName.equals("hpts")) {        //胡牌提示
                hupaitishi = "true";
            } else {
            }
        });
    }

    public int payForCreateRoom() {
        int gold = 10;
        if (panshu.equals("8")) {
            gold = 10;
        } else if (panshu.equals("16")) {
            gold = 20;
        } else if (panshu.equals("4")) {
            gold = 5;
        }
        return gold;
    }

    public int payForJoinRoom() {
        int gold = 10;
        if (panshu.equals("8")) {
            gold = 10;
        } else if (panshu.equals("16")) {
            gold = 20;
        } else if (panshu.equals("4")) {
            gold = 5;
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

    public String getBabashuang() {
        return babashuang;
    }

    public void setBabashuang(String babashuang) {
        this.babashuang = babashuang;
    }

    public String getBaosanzui() {
        return baosanzui;
    }

    public void setBaosanzui(String baosanzui) {
        this.baosanzui = baosanzui;
    }

    public String getHupaitishi() {
        return hupaitishi;
    }

    public void setHupaitishi(String hupaitishi) {
        this.hupaitishi = hupaitishi;
    }
}
