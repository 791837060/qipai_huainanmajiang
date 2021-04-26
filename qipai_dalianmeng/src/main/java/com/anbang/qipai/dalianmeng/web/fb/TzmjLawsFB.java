package com.anbang.qipai.dalianmeng.web.fb;

import java.util.List;

public class TzmjLawsFB {

    private String panshu;                      //盘数
    private String renshu;                      //人数
    private String gps = "false";               //GPS
    private String voice = "false";             //语音
    private String tuoguan = "0";               //进入托管时间
    private String tuoguanjiesan = "false";     //托管解散
    private String lixianchengfa = "false";     //离线惩罚
    private String banVoice = "false";          //禁止语音
    private String banJiesan = "false";         //禁止解散
    private String dairuzongfen = "false";      //带入总分

    private String jinyuanzi = "false";         //进园子

    private String pengfang = "false";          //碰访
    private String fengding = "false";          //封顶
    private String shukazi = "false";           //数卡子
    private String tingpaikejian = "false";     //听牌可见

    public TzmjLawsFB(List<String> lawNames) {
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
            } else if (lawName.equals("pf")) {      //碰访
                pengfang = "true";
            } else if (lawName.equals("bbfd")) {    //八倍封顶
                fengding = "8";
            } else if (lawName.equals("slbfd")) {   //十六倍封顶
                fengding = "16";
            } else if (lawName.equals("skz")) {     //数卡子
                shukazi = "true";
            } else if (lawName.equals("tgjs")) {    //托管解散
                tuoguanjiesan = "true";
            } else if (lawName.equals("lxcf")) {    //离线惩罚
                lixianchengfa = "true";
            } else if (lawName.equals("banVoice")) {//禁止语音
                banVoice = "true";
            } else if (lawName.equals("banJiesan")) {//禁止解散
                banJiesan = "true";
            } else if (lawName.equals("drzf")) {    //带入总分
                dairuzongfen = "true";
            } else if (lawName.equals("tpkj")) {    //听牌可见
                tingpaikejian = "true";
            }  else {
            }
        });
    }

    public int payForCreateRoom() {
        int gold = 1;
        if (panshu.equals("8")&&renshu.equals("2")){
            gold = 2;
        }else if (panshu.equals("8")&&renshu.equals("3")){
            gold = 3;
        }else if (panshu.equals("8")&&renshu.equals("4")){
            gold = 4;
        }else if (panshu.equals("16")&&renshu.equals("2")){
            gold = 4;
        }else if (panshu.equals("16")&&renshu.equals("3")){
            gold = 6;
        }else if (panshu.equals("16")&&renshu.equals("4")){
            gold = 8;
        }
        return gold;
    }

    public int payForJoinRoom() {
        int gold = 1;
        if (panshu.equals("8")&&renshu.equals("2")){
            gold = 2;
        }else if (panshu.equals("8")&&renshu.equals("3")){
            gold = 3;
        }else if (panshu.equals("8")&&renshu.equals("4")){
            gold = 4;
        }else if (panshu.equals("16")&&renshu.equals("2")){
            gold = 4;
        }else if (panshu.equals("16")&&renshu.equals("3")){
            gold = 6;
        }else if (panshu.equals("16")&&renshu.equals("4")){
            gold = 8;
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

    public String getPengfang() {
        return pengfang;
    }

    public void setPengfang(String pengfang) {
        this.pengfang = pengfang;
    }

    public String getFengding() {
        return fengding;
    }

    public void setFengding(String fengding) {
        this.fengding = fengding;
    }

    public String getShukazi() {
        return shukazi;
    }

    public void setShukazi(String shukazi) {
        this.shukazi = shukazi;
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

    public String getTingpaikejian() {
        return tingpaikejian;
    }

    public void setTingpaikejian(String tingpaikejian) {
        this.tingpaikejian = tingpaikejian;
    }
}
