package com.anbang.qipai.game.web.fb;

import java.util.List;

public class HuainanmjLawsFB {

    private String panshu;
    private String renshu;
    private String maipao = "false";
    private String maima = "false";

    private String gps = "false";
    private String voice = "false";
    private String tuoguan = "0";           //进入托管时间
    private String tuoguanjiesan = "false"; //托管盘结束解散
    private String lixianchengfa = "false"; //离线惩罚
    private String banVoice = "false";      //禁止语音
    private String banJiesan = "false";     //禁止解散


    public HuainanmjLawsFB(List<String> lawNames) {
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
            } else if (lawName.equals("mp")) {// 叫跑
                maipao = "true";
            }else if (lawName.equals("mm")) {// 买马
                maima = "true";
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

    public void setPanshu(String panshu) {
        this.panshu = panshu;
    }

    public String getRenshu() {
        return renshu;
    }

    public void setRenshu(String renshu) {
        this.renshu = renshu;
    }

//    public String getQufengpai() {
//        return qufengpai;
//    }
//
//    public void setQufengpai(String qufengpai) {
//        this.qufengpai = qufengpai;
//    }
//
//    public String getZhikezimo() {
//        return zhikezimo;
//    }
//
//    public void setZhikezimo(String zhikezimo) {
//        this.zhikezimo = zhikezimo;
//    }
//
//    public String getHongzhonglaizi() {
//        return hongzhonglaizi;
//    }
//
//    public void setHongzhonglaizi(String hongzhonglaizi) {
//        this.hongzhonglaizi = hongzhonglaizi;
//    }
//
//    public String getKeyidahu() {
//        return keyidahu;
//    }
//
//    public void setKeyidahu(String keyidahu) {
//        this.keyidahu = keyidahu;
//    }

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

    public String getMaipao() {
        return maipao;
    }

    public void setMaipao(String maipao) {
        this.maipao = maipao;
    }
    public String getMaima() {
        return maima;
    }

    public void setMaima(String maima) {
        this.maima = maima;
    }
}
