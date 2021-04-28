package com.anbang.qipai.maanshanmajiang.web.vo;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MaanshanMajiangHushu;

public class HufenVO {
    private boolean hu;             //胡
    private boolean zimoHu;         //自摸胡

    private boolean xiaohu;         //小胡
    private int bazhi;              //八支
    private boolean shuangbazhi;    //双八支
    private boolean fengyise;       //风一色
    private boolean qingyise;       //清一色
    private boolean hunyise;        //混一色
    private boolean duiduihu;       //对对胡
    private boolean dadiaoche;      //大吊车
    private int minggang;           //明杠
    private int angang;             //暗杠
    private boolean tongtian;       //通天（一条龙）
    private boolean liulian;        //六连
    private boolean shuangliulian;  //双六连
    private int wutong;             //五同
    private int shuangwutong;       //双五同
    private boolean sihe;           //四核
    private boolean shuangsihe;     //双四核
    private boolean kuyazhi;        //枯支压
    private int sanzhangzaishou;    //三张在手
    private int sanzhangpengchu;    //三张碰出
    private boolean yadang;         //压档
    private boolean shixiao;        //十小
    private boolean quanxiao;       //全小
    private boolean shilao;         //十老
    private boolean quanlao;        //全老
    private boolean budongshou;     //不动手
    private int shuangpuzi;         //双铺子
    private boolean wamo;           //挖摸
    private boolean qingshuidana;   //清水大拿
    private boolean hunshuidana;    //浑水大拿

    private double value;              //总分
    private double tiwaixunhuan;       //体外循环分数

    public HufenVO() {

    }

    public HufenVO(MaanshanMajiangHushu playerHufen) {
        hu = playerHufen.isHu();                            //胡
        xiaohu = playerHufen.isXiaohu();                    //小胡
        zimoHu = playerHufen.isZimoHu();                    //自摸胡
        bazhi = playerHufen.getBazhi();                     //八支
        shuangbazhi = playerHufen.isShuangbazhi();          //双八支
        fengyise = playerHufen.isFengyise();                //风一色
        qingyise = playerHufen.isQingyise();                //清一色
        hunyise = playerHufen.isHunyise();                  //混一色
        duiduihu = playerHufen.isDuiduihu();                //对对胡
        dadiaoche = playerHufen.isDadiaoche();              //大吊车
        minggang = playerHufen.getMinggang();               //明杠
        angang = playerHufen.getAngang();                   //暗杠
        tongtian = playerHufen.isTongtian();                //通天（一条龙）
        liulian = playerHufen.isLiulian();                  //六连
        shuangliulian = playerHufen.isShuangliulian();      //双六连
        wutong = playerHufen.getWutong();                   //五同
        shuangwutong = playerHufen.getShuangwutong();       //双五同
        sihe = playerHufen.isSihe();                        //四核
        shuangsihe = playerHufen.isShuangsihe();            //双四核
        kuyazhi = playerHufen.isKuyazhi();                  //枯支压
        sanzhangzaishou = playerHufen.getSanzhangzaishou(); //三张在手
        sanzhangpengchu = playerHufen.getSanzhangpengchu(); //三张碰出
        yadang = playerHufen.isYadang();                    //压档
        shixiao = playerHufen.isShixiao();                  //十小
        quanxiao = playerHufen.isQuanxiao();                //全小
        shilao = playerHufen.isShilao();                    //十老
        quanlao = playerHufen.isQuanlao();                  //全老
        budongshou = playerHufen.isBudongshou();            //不动手
        shuangpuzi = playerHufen.getShuangpuzi();           //双铺子
        wamo = playerHufen.isWamo();                        //挖摸
        qingshuidana = playerHufen.isQingshuidana();        //清水大拿
        hunshuidana = playerHufen.isHunshuidana();          //浑水大拿
        value = playerHufen.getValue();                     //总分
        tiwaixunhuan = playerHufen.getTiwaixunhuan();       //体外循环
    }

    public boolean isHu() {
        return hu;
    }

    public void setHu(boolean hu) {
        this.hu = hu;
    }

    public boolean isZimoHu() {
        return zimoHu;
    }

    public void setZimoHu(boolean zimoHu) {
        this.zimoHu = zimoHu;
    }

    public int getBazhi() {
        return bazhi;
    }

    public void setBazhi(int bazhi) {
        this.bazhi = bazhi;
    }

    public boolean isShuangbazhi() {
        return shuangbazhi;
    }

    public void setShuangbazhi(boolean shuangbazhi) {
        this.shuangbazhi = shuangbazhi;
    }

    public boolean isFengyise() {
        return fengyise;
    }

    public void setFengyise(boolean fengyise) {
        this.fengyise = fengyise;
    }

    public boolean isQingyise() {
        return qingyise;
    }

    public void setQingyise(boolean qingyise) {
        this.qingyise = qingyise;
    }

    public boolean isHunyise() {
        return hunyise;
    }

    public void setHunyise(boolean hunyise) {
        this.hunyise = hunyise;
    }

    public boolean isDuiduihu() {
        return duiduihu;
    }

    public void setDuiduihu(boolean duiduihu) {
        this.duiduihu = duiduihu;
    }

    public boolean isDadiaoche() {
        return dadiaoche;
    }

    public void setDadiaoche(boolean dadiaoche) {
        this.dadiaoche = dadiaoche;
    }

    public int getMinggang() {
        return minggang;
    }

    public void setMinggang(int minggang) {
        this.minggang = minggang;
    }

    public int getAngang() {
        return angang;
    }

    public void setAngang(int angang) {
        this.angang = angang;
    }

    public boolean isTongtian() {
        return tongtian;
    }

    public void setTongtian(boolean tongtian) {
        this.tongtian = tongtian;
    }

    public boolean isLiulian() {
        return liulian;
    }

    public void setLiulian(boolean liulian) {
        this.liulian = liulian;
    }

    public boolean isShuangliulian() {
        return shuangliulian;
    }

    public void setShuangliulian(boolean shuangliulian) {
        this.shuangliulian = shuangliulian;
    }

    public int getWutong() {
        return wutong;
    }

    public void setWutong(int wutong) {
        this.wutong = wutong;
    }

    public int getShuangwutong() {
        return shuangwutong;
    }

    public void setShuangwutong(int shuangwutong) {
        this.shuangwutong = shuangwutong;
    }

    public boolean isSihe() {
        return sihe;
    }

    public void setSihe(boolean sihe) {
        this.sihe = sihe;
    }

    public boolean isShuangsihe() {
        return shuangsihe;
    }

    public void setShuangsihe(boolean shuangsihe) {
        this.shuangsihe = shuangsihe;
    }

    public boolean isKuyazhi() {
        return kuyazhi;
    }

    public void setKuyazhi(boolean kuyazhi) {
        this.kuyazhi = kuyazhi;
    }

    public int getSanzhangzaishou() {
        return sanzhangzaishou;
    }

    public void setSanzhangzaishou(int sanzhangzaishou) {
        this.sanzhangzaishou = sanzhangzaishou;
    }

    public int getSanzhangpengchu() {
        return sanzhangpengchu;
    }

    public void setSanzhangpengchu(int sanzhangpengchu) {
        this.sanzhangpengchu = sanzhangpengchu;
    }

    public boolean isYadang() {
        return yadang;
    }

    public void setYadang(boolean yadang) {
        this.yadang = yadang;
    }

    public boolean isShixiao() {
        return shixiao;
    }

    public void setShixiao(boolean shixiao) {
        this.shixiao = shixiao;
    }

    public boolean isQuanxiao() {
        return quanxiao;
    }

    public void setQuanxiao(boolean quanxiao) {
        this.quanxiao = quanxiao;
    }

    public boolean isShilao() {
        return shilao;
    }

    public void setShilao(boolean shilao) {
        this.shilao = shilao;
    }

    public boolean isQuanlao() {
        return quanlao;
    }

    public void setQuanlao(boolean quanlao) {
        this.quanlao = quanlao;
    }

    public boolean isBudongshou() {
        return budongshou;
    }

    public void setBudongshou(boolean budongshou) {
        this.budongshou = budongshou;
    }

    public int getShuangpuzi() {
        return shuangpuzi;
    }

    public void setShuangpuzi(int shuangpuzi) {
        this.shuangpuzi = shuangpuzi;
    }

    public boolean isWamo() {
        return wamo;
    }

    public void setWamo(boolean wamo) {
        this.wamo = wamo;
    }

    public boolean isQingshuidana() {
        return qingshuidana;
    }

    public void setQingshuidana(boolean qingshuidana) {
        this.qingshuidana = qingshuidana;
    }

    public boolean isHunshuidana() {
        return hunshuidana;
    }

    public void setHunshuidana(boolean hunshuidana) {
        this.hunshuidana = hunshuidana;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getTiwaixunhuan() {
        return tiwaixunhuan;
    }

    public void setTiwaixunhuan(double tiwaixunhuan) {
        this.tiwaixunhuan = tiwaixunhuan;
    }

    public boolean isXiaohu() {
        return xiaohu;
    }

    public void setXiaohu(boolean xiaohu) {
        this.xiaohu = xiaohu;
    }
}
