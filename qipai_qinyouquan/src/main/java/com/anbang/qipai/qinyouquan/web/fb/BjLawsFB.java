package com.anbang.qipai.qinyouquan.web.fb;

import java.util.List;

public class BjLawsFB {
    private String panshu;                   //盘数
    private String renshu;                   //人数
    private String quanhonghei = "false";    //全黑红
    private String quanshun = "false";       //全顺
    private String tongguan = "false";       //通关
    private String sanqing = "false";        //三清
    private String tonghuadatou = "false";   //同花打头
    private String shunqingdatou = "false";  //顺清打头
    private String shuangsantiao = "false";  //双三条
    private String sizhang = "false";        //四张
    private String quanshunqing = "false";   //全顺清
    private String shuangshunqing = "false"; //双顺清
    private String quansantiao = "false";    //全三条
    private String qipai = "false";          //可以弃牌
    private String xipaigudingfen = "false"; //喜牌固定分
    private String gps = "false";            //GPS
    private String voice = "false";          //语音
    private String jisumoshi = "false";      //极速模式
    private String tuoguan = "0";            //进入托管时间
    private String tuoguanjiesan = "false";  //托管盘结束解散
    private String lixianchengfa = "false";  //离线惩罚
    private String jinyuanzi = "false";      //进园子
    private String jinzhiyuyin = "false";    //禁止语音

    private String banVoice = "false";       //禁止语音
    private String banJiesan = "false";      //禁止解散
    private String dairuzongfen = "false";   //带入总分

    public BjLawsFB() {
    }

    public BjLawsFB(List<String> lawNames) {
        lawNames.forEach((lawName) -> {
            if (lawName.equals("lj")) {                     //6局
                panshu = "6";
            } else if (lawName.equals("bj")) {              //8局
                panshu = "8";
            } else if (lawName.equals("shj")) {             //10局
                panshu = "10";
            } else if (lawName.equals("slj")) {             //16局
                panshu = "16";
            } else if (lawName.equals("esj")) {             //20局
                panshu = "20";
            } else if (lawName.equals("er")) {              //二人
                renshu = "2";
            } else if (lawName.equals("sanr")) {            //三人
                renshu = "3";
            } else if (lawName.equals("sir")) {             //四人
                renshu = "4";
            } else if (lawName.equals("wr")) {              //五人
                renshu = "5";
            } else if (lawName.equals("btg")) {             //不托管
                tuoguan = "0";
            } else if (lawName.equals("tsw")) {             //15秒托管
                tuoguan = "15";
            } else if (lawName.equals("tss")) {             //30秒托管
                tuoguan = "30";
            } else if (lawName.equals("ssw")) {             //45秒托管
                tuoguan = "45";
            } else if (lawName.equals("tls")) {             //60秒托管
                tuoguan = "60";
            } else if (lawName.equals("tjs")) {             //90秒托管
                tuoguan = "90";
            } else if (lawName.equals("tybe")) {            //120秒托管
                tuoguan = "120";
            } else if (lawName.equals("tebs")) {            //240秒托管
                tuoguan = "240";
            } else if (lawName.equals("tsb")) {             //300秒托管
                tuoguan = "300";
            } else if (lawName.equals("qhh")) {             //全红黑
                quanhonghei = "true";
            } else if (lawName.equals("qs")) {              //全顺
                quanshun = "true";
            } else if (lawName.equals("tg")) {              //通关
                tongguan = "true";
            } else if (lawName.equals("sq")) {              //三清
                sanqing = "true";
            } else if (lawName.equals("thdt")) {            //同花打头
                tonghuadatou = "true";
            } else if (lawName.equals("sqdt")) {            //顺清打头
                shunqingdatou = "true";
            } else if (lawName.equals("sst")) {             //双三条
                shuangsantiao = "true";
            } else if (lawName.equals("sz")) {              //四张
                sizhang = "true";
            } else if (lawName.equals("ssq")) {             //双顺清
                shuangshunqing = "true";
            } else if (lawName.equals("qsq")) {             //全顺清
                quanshunqing = "true";
            } else if (lawName.equals("qst")) {             //全三条
                quansantiao = "true";
            } else if (lawName.equals("qp")) {              //可以弃牌
                qipai = "true";
            } else if (lawName.equals("xpgdf")) {           //喜牌固定分
                xipaigudingfen = "true";
            } else if (lawName.equals("gps")) {             //GPS
                gps = "true";
            } else if (lawName.equals("voice")) {           //语音
                voice = "true";
            } else if (lawName.equals("jsms")) {            //极速模式
                jisumoshi = "true";
            } else if (lawName.equals("tgjs")) {            //托管解散
                tuoguanjiesan = "true";
            } else if (lawName.equals("lxcf")) {            //离线惩罚
                lixianchengfa = "true";
            } else if (lawName.equals("jyz")) {             //进园子
                jinyuanzi = "true";
            } else if (lawName.equals("jzyy")) {            //禁止语音
                jinzhiyuyin = "true";
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
        int gold = 20;
        switch (panshu) {
            case "8":
                gold = 15;
                break;
            case "10":
                gold = 15;
                break;
            case "16":
                gold = 30;
                break;
            case "20":
                gold = 30;
                break;
        }
        return gold;
    }

    public int payForJoinRoom() {
        int gold = 20;
        switch (panshu) {
            case "8":
                gold = 15;
                break;
            case "10":
                gold = 15;
                break;
            case "16":
                gold = 30;
                break;
            case "20":
                gold = 30;
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

    public String getTuoguan() {
        return tuoguan;
    }

    public void setTuoguan(String tuoguan) {
        this.tuoguan = tuoguan;
    }

    public String getQuanhonghei() {
        return quanhonghei;
    }

    public void setQuanhonghei(String quanhonghei) {
        this.quanhonghei = quanhonghei;
    }

    public String getQuanshun() {
        return quanshun;
    }

    public void setQuanshun(String quanshun) {
        this.quanshun = quanshun;
    }

    public String getTongguan() {
        return tongguan;
    }

    public void setTongguan(String tongguan) {
        this.tongguan = tongguan;
    }

    public String getSanqing() {
        return sanqing;
    }

    public void setSanqing(String sanqing) {
        this.sanqing = sanqing;
    }

    public String getShunqingdatou() {
        return shunqingdatou;
    }

    public void setShunqingdatou(String shunqingdatou) {
        this.shunqingdatou = shunqingdatou;
    }

    public String getShuangsantiao() {
        return shuangsantiao;
    }

    public void setShuangsantiao(String shuangsantiao) {
        this.shuangsantiao = shuangsantiao;
    }

    public String getSizhang() {
        return sizhang;
    }

    public void setSizhang(String sizhang) {
        this.sizhang = sizhang;
    }

    public String getQuanshunqing() {
        return quanshunqing;
    }

    public void setQuanshunqing(String quanshunqing) {
        this.quanshunqing = quanshunqing;
    }

    public String getQuansantiao() {
        return quansantiao;
    }

    public void setQuansantiao(String quansantiao) {
        this.quansantiao = quansantiao;
    }

    public String getQipai() {
        return qipai;
    }

    public void setQipai(String qipai) {
        this.qipai = qipai;
    }

    public String getXipaigudingfen() {
        return xipaigudingfen;
    }

    public void setXipaigudingfen(String xipaigudingfen) {
        this.xipaigudingfen = xipaigudingfen;
    }

    public String getShuangshunqing() {
        return shuangshunqing;
    }

    public void setShuangshunqing(String shuangshunqing) {
        this.shuangshunqing = shuangshunqing;
    }

    public String getTonghuadatou() {
        return tonghuadatou;
    }

    public void setTonghuadatou(String tonghuadatou) {
        this.tonghuadatou = tonghuadatou;
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

    public String getJisumoshi() {
        return jisumoshi;
    }

    public void setJisumoshi(String jisumoshi) {
        this.jisumoshi = jisumoshi;
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

    public String getJinyuanzi() {
        return jinyuanzi;
    }

    public void setJinyuanzi(String jinyuanzi) {
        this.jinyuanzi = jinyuanzi;
    }

    public String getJinzhiyuyin() {
        return jinzhiyuyin;
    }

    public void setJinzhiyuyin(String jinzhiyuyin) {
        this.jinzhiyuyin = jinzhiyuyin;
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
