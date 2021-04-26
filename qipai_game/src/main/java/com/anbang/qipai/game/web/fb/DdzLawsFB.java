package com.anbang.qipai.game.web.fb;

import java.util.List;

public class DdzLawsFB {
    private String panshu;
    private String renshu;
    private String fdbs;
    private String qxp = "false";
    private String szfbxp = "false";
    private String gps = "false";
    private String voice = "false";
    private String swbq = "false";
    private String jfqdz = "false";
    private String banVoice = "false";      //禁止语音
    private String banJiesan="false";       //禁止解散
    private String xianshishoupai="false";  //显示手牌

    public DdzLawsFB(List<String> lawNames) {
        lawNames.forEach((lawName) -> {
            if (lawName.equals("bj")) {// 八局
                panshu = "8";
            } else if (lawName.equals("sj")) {// 四局
                panshu = "4";
            } else if (lawName.equals("slj")) {// 十六局
                panshu = "16";
            } else if (lawName.equals("er")) {// 二人
                renshu = "2";
            } else if (lawName.equals("sanr")) {// 三人
                renshu = "3";
            } else if (lawName.equals("qxp")) {// 去小牌
                qxp = "true";
            } else if (lawName.equals("szfbxp")) {// 三张分不洗牌
                szfbxp = "true";
            } else if (lawName.equals("bfd")) {// 不封顶
                fdbs = "0";
            } else if (lawName.equals("bb")) {// 八倍
                fdbs = "8";
            } else if (lawName.equals("slb")) {// 十六倍
                fdbs = "16";
            } else if (lawName.equals("seb")) {// 三十二倍
                fdbs = "32";
            } else if (lawName.equals("lsb")) {// 六十四倍
                fdbs = "64";
            } else if (lawName.equals("gps")) {
                gps = "true";
            } else if (lawName.equals("voice")) {
                voice = "true";
            } else if (lawName.equals("swbq")) {
                swbq = "true";
            } else if (lawName.equals("jfqdz")) {
                jfqdz = "true";
            } else if (lawName.equals("banVoice")) {
                banVoice = "true";
            } else if (lawName.equals("banJiesan")) {
                banJiesan = "true";
            } else if (lawName.equals("xssp")) {        //显示手牌
                xianshishoupai = "true";
            }  else {

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

    public String getQxp() {
        return qxp;
    }

    public void setQxp(String qxp) {
        this.qxp = qxp;
    }

    public String getSzfbxp() {
        return szfbxp;
    }

    public void setSzfbxp(String szfbxp) {
        this.szfbxp = szfbxp;
    }

    public String getFdbs() {
        return fdbs;
    }

    public void setFdbs(String fdbs) {
        this.fdbs = fdbs;
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

    public String getSwbq() {
        return swbq;
    }

    public void setSwbq(String swbq) {
        this.swbq = swbq;
    }

    public String getJfqdz() {
        return jfqdz;
    }

    public void setJfqdz(String jfqdz) {
        this.jfqdz = jfqdz;
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

    public String getXianshishoupai() {
        return xianshishoupai;
    }

    public void setXianshishoupai(String xianshishoupai) {
        this.xianshishoupai = xianshishoupai;
    }
}
