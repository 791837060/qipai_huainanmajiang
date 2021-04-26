package com.anbang.qipai.game.web.fb;

import java.util.List;

public class HsbLawsFB {
    private String panshu;
    private String renshu;
//    private String baoziScore = "0";
//    private String lowScore = "0";
//    private String addScore = "0";
//    private String kundou = "0";
    private String jiesuanfangshi = "0";
    private String pengzhaoTishi = "false";
    private String youzixianshi = "false";
    private String fanxun = "false";
    private String xiaoerbusuanfen = "false";
    private String tuoguan = "0";       //进入托管时间
    private String gps = "false";
    private String voice = "false";

    public HsbLawsFB(List<String> lawNames) {
        lawNames.forEach((lawName) -> {
            if (lawName.equals("sanj")) {// 三局
                panshu = "3";
            } else if (lawName.equals("lj")) {// 六局
                panshu = "6";
            } else if (lawName.equals("serj")) {// 十二局
                panshu = "12";
            } else if (lawName.equals("er")) {// 二人
                renshu = "2";
            } else if (lawName.equals("sanr")) {// 三人
                renshu = "3";
            } else if (lawName.equals("quankai")) {// 全开
                jiesuanfangshi = "0";
            } else if (lawName.equals("fangpaokai")) {// 放炮开
                jiesuanfangshi = "1";
            } else if (lawName.equals("dianpaoquanpei")) {// 点炮全赔
                jiesuanfangshi = "2";
            } else if (lawName.equals("pzts")) {// 碰招提示
                pengzhaoTishi = "true";
            } else if (lawName.equals("yzxs")) {// 油子显示
                youzixianshi = "true";
            } else if (lawName.equals("fx")) {// 带翻训
                fanxun = "true";
            } else if (lawName.equals("xebsf")) {// 小二不算分
                xiaoerbusuanfen = "true";
            } else if (lawName.equals("gps")) {
                gps = "true";
            } else if (lawName.equals("voice")) {
                voice = "true";
            } else if (lawName.equals("btg")) {
                tuoguan = "0";
            } else if (lawName.equals("tsw")) {
                tuoguan = "15";
            } else if (lawName.equals("tss")) {
                tuoguan = "30";
            } else if (lawName.equals("tls")) {
                tuoguan = "60";
            } else if (lawName.equals("tjs")) {
                tuoguan = "90";
            } else if (lawName.equals("tybe")) {
                tuoguan = "120";
            } else if (lawName.equals("tebs")) {
                tuoguan = "240";
            } else if (lawName.equals("tsb")) {
                tuoguan = "300";
            } else {

            }
        });
    }

    public int payForCreateRoom() {
        int gold = 15;
        switch (panshu) {
            case "3":
                gold = 15;
                break;
            case "6":
                gold = 30;
                break;
            case "12":
                gold = 60;
                break;
        }
        return gold;
    }

    public int payForJoinRoom() {
        int gold = 15;
        switch (panshu) {
            case "3":
                gold = 30;
                break;
            case "6":
                gold = 60;
                break;
            case "12":
                gold = 120;
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

    public String getJiesuanfangshi() {
        return jiesuanfangshi;
    }

    public void setJiesuanfangshi(String jiesuanfangshi) {
        this.jiesuanfangshi = jiesuanfangshi;
    }

    public String getPengzhaoTishi() {
        return pengzhaoTishi;
    }

    public void setPengzhaoTishi(String pengzhaoTishi) {
        this.pengzhaoTishi = pengzhaoTishi;
    }

    public String getYouzixianshi() {
        return youzixianshi;
    }

    public void setYouzixianshi(String youzixianshi) {
        this.youzixianshi = youzixianshi;
    }

    public String getFanxun() {
        return fanxun;
    }

    public void setFanxun(String fanxun) {
        this.fanxun = fanxun;
    }

    public String getXiaoerbusuanfen() {
        return xiaoerbusuanfen;
    }

    public void setXiaoerbusuanfen(String xiaoerbusuanfen) {
        this.xiaoerbusuanfen = xiaoerbusuanfen;
    }

    public String getTuoguan() {
        return tuoguan;
    }

    public void setTuoguan(String tuoguan) {
        this.tuoguan = tuoguan;
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
}
