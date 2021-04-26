package com.anbang.qipai.dalianmeng.web.fb;

import java.util.List;

public class TdhLawsFB {

    private String panshu;
    private String renshu;
    private String qiangganghu = "false";
    private String qidui = "false";
    private String keyidahu = "false";
    private String niaoshu="0";
    private String gps = "false";
    private String voice = "false";
    private String tuoguan = "0";           //进入托管时间
    private String tuoguanjiesan = "false"; //托管盘结束解散
    private String lixianchengfa = "false"; //离线惩罚
    private String banVoice = "false";      //禁止语音
    private String banJiesan = "false";     //禁止解散
    private String dairuzongfen = "false";  //带入总分



    public TdhLawsFB(List<String> lawNames) {
        lawNames.forEach((lawName) -> {
            if (lawName.equals("bj")) {// 八局
                panshu = "8";
            } else if (lawName.equals("slj")) {// 十六局
                panshu = "16";
            } else if (lawName.equals("er")) {// 二人
                renshu = "2";
            } else if (lawName.equals("sanr")) {// 三人
                renshu = "3";
            } else if (lawName.equals("sir")) {// 四人
                renshu = "4";
            } else if (lawName.equals("qgh")) {// 抢杠胡
                qiangganghu = "true";
            } else if (lawName.equals("qd")) {// 七对
                qidui = "true";
            } else if (lawName.equals("ezn")) {// 两只鸟
                niaoshu = "2";
            } else if (lawName.equals("szn")) {// 四只鸟
                niaoshu = "4";
            } else if (lawName.equals("lzn")) {// 六只鸟
                niaoshu = "6";
            } else if (lawName.equals("kydh")) {// 可以大胡
                keyidahu = "true";
            } else if (lawName.equals("gps")) {// gps
                gps = "true";
            } else if (lawName.equals("voice")) {// voice
                voice = "true";
            } else if (lawName.equals("btg")) {         //不托管
                tuoguan = "0";
            } else if (lawName.equals("tsw")) {         //15秒后进入托管
                tuoguan = "15";
            } else if (lawName.equals("tss")) {         //30秒后进入托管
                tuoguan = "30";
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
            } else {
            }
        });
    }


    public int payForCreateRoom() {
        int gold = 14;
        if (panshu.equals("8")) {
            gold = 7;
        } else if (panshu.equals("16")) {
            gold = 14;
        }
        return gold;
    }

    public int payForJoinRoom() {
        int gold = 14;
        if (panshu.equals("8")) {
            gold = 7;
        } else if (panshu.equals("16")) {
            gold = 14;
        }
        return gold;
    }


    public String getPanshu() {
        return panshu;
    }

    public String getRenshu() {
        return renshu;
    }

    public String getQiangganghu() {
        return qiangganghu;
    }

    public String getQidui() {
        return qidui;
    }

    public String getKeyidahu() {
        return keyidahu;
    }

    public String getNiaoshu() {
        return niaoshu;
    }

    public String getGps() {
        return gps;
    }

    public String getVoice() {
        return voice;
    }

    public String getTuoguan() {
        return tuoguan;
    }

    public String getTuoguanjiesan() {
        return tuoguanjiesan;
    }

    public String getLixianchengfa() {
        return lixianchengfa;
    }

    public String getBanVoice() {
        return banVoice;
    }

    public String getBanJiesan() {
        return banJiesan;
    }

    public String getDairuzongfen() {
        return dairuzongfen;
    }
}
