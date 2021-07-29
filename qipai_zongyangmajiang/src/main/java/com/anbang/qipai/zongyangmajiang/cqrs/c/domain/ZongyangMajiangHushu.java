package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.XushupaiCategory;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.shoupai.ShoupaiPaiXing;

import java.util.List;

/**
 * 结算规则
 * 自摸：所有人跟胡家结算胡牌牌型分数；杠分单独结算
 * 放炮：其他玩家不用与胡家结算，只有放炮的人与胡家结算胡牌牌型分数；杠分单独结算
 */
public class ZongyangMajiangHushu {
    private boolean hu;                 //胡
    private boolean zimoHu;             //自摸胡；
    private boolean qingyise;           //清一色
    private boolean hunyise;            //混一色
    private boolean fengyise;           //风一色
    private boolean paofeng;            //跑风
    private boolean paofenggang;        //跑风杠
    private boolean wuzuan;             //无钻(没有鬼牌)
    private boolean wuguizuandong;      //乌龟钻洞
    private boolean jiuzhi;             //九支
    private boolean gangshangkaihua;    //杠上开花
    private boolean qiangganghu;        //抢杠胡
    private boolean qixiaodui;          //七小对
    private boolean haohuaqixiaodui;    //豪华七小对
    private boolean haohuaqixiaodui1;    //豪华七小对
    private boolean haohuaqixiaodui2;    //豪华七小对
    private boolean yitiaolong;         //一条龙
    private boolean shisanlan;          //十三烂
    private boolean hongzhongbaiban;    //红中白板
    private double  huapai;             //花牌
    private int value;

    public void calculate(double huapaiCount, MajiangPlayer player) {
        int hushu = 0;
        if (hu || zimoHu) {
            hushu = 2;
        }
        if (wuzuan){
            hushu += 1;
        }
        if(jiuzhi){
            hushu += 1;
        }
        if (hongzhongbaiban){
            hushu += 1;
        }
        if (paofeng){
            hushu += 5;
        }
        if (qixiaodui){
            hushu = 5;

        }if (paofenggang){
            hushu = 10;
        }
        if (wuguizuandong){
            hushu = 10;
        }
        if(fengyise){
            hushu = 30;
        }
        if (gangshangkaihua){
            hushu += 5;
        }
        if (qingyise){
            hushu = 20;
        }
        if (yitiaolong) {
            hushu = 15;
        }
        if (hunyise){
            hushu = 10;
        }
        if (haohuaqixiaodui) {
            hushu = 10;
        }
        if (haohuaqixiaodui1){
            hushu = 15;
        }
        if (haohuaqixiaodui2){
            hushu = 20;
        }
        if (shisanlan){
            hushu = 25;
        }
        if(paofenggang && gangshangkaihua){
            hushu += 10;
        }
        List<MajiangPai> fangruShoupaiList = player.getFangruShoupaiList();
        if (!fangruShoupaiList.contains(XushupaiCategory.tong) && fangruShoupaiList.contains(XushupaiCategory.tiao)){
            hushu+=2;
        }else if (!fangruShoupaiList.contains(XushupaiCategory.wan) && fangruShoupaiList.contains(XushupaiCategory.tong)){
            hushu+=2;
        }
        else if (!fangruShoupaiList.contains(XushupaiCategory.wan) && fangruShoupaiList.contains(XushupaiCategory.tiao)){
            hushu+=2;
        }else if (!fangruShoupaiList.contains(XushupaiCategory.wan)){
            hushu+=1;
        }
        else if (!fangruShoupaiList.contains(XushupaiCategory.tiao)){
            hushu+=1;
        }else if (!fangruShoupaiList.contains(XushupaiCategory.tong)){
            hushu+=1;
        }
        huapai = huapaiCount;
        hushu+=huapai;
        value = hushu;
    }


    public int jiesuan(int delta) {
        return value += delta;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isQingyise() {
        return qingyise;
    }

    public void setQingyise(boolean qingyise) {
        this.qingyise = qingyise;
    }

    public boolean isQixiaodui() {
        return qixiaodui;
    }

    public void setQixiaodui(boolean qixiaodui) {
        this.qixiaodui = qixiaodui;
    }

    public boolean isHaohuaqixiaodui() {
        return haohuaqixiaodui;
    }

    public void setHaohuaqixiaodui(boolean haohuaqixiaodui) {
        this.haohuaqixiaodui = haohuaqixiaodui;
    }

    public boolean isYitiaolong() {
        return yitiaolong;
    }

    public void setYitiaolong(boolean yitiaolong) {
        this.yitiaolong = yitiaolong;
    }

    public boolean isGangshangkaihua() {
        return gangshangkaihua;
    }

    public void setGangshangkaihua(boolean gangshangkaihua) {
        this.gangshangkaihua = gangshangkaihua;
    }

    public boolean isQiangganghu() {
        return qiangganghu;
    }

    public void setQiangganghu(boolean qiangganghu) {
        this.qiangganghu = qiangganghu;
    }

    public boolean isHunyise() {
        return hunyise;
    }

    public void setHunyise(boolean hunyise) {
        this.hunyise = hunyise;
    }

    public boolean isFengyise() {
        return fengyise;
    }

    public void setFengyise(boolean fengyise) {
        this.fengyise = fengyise;
    }

    public boolean isPaofeng() {
        return paofeng;
    }

    public void setPaofeng(boolean paofeng) {
        this.paofeng = paofeng;
    }

    public boolean isPaofenggang() {
        return paofenggang;
    }

    public void setPaofenggang(boolean paofenggang) {
        this.paofenggang = paofenggang;
    }

    public boolean isWuzuan() {
        return wuzuan;
    }

    public void setWuzuan(boolean wuzuan) {
        this.wuzuan = wuzuan;
    }

    public boolean isWuguizuandong() {
        return wuguizuandong;
    }

    public void setWuguizuandong(boolean wuguizuandong) {
        this.wuguizuandong = wuguizuandong;
    }

    public boolean isJiuzhi() {
        return jiuzhi;
    }

    public void setJiuzhi(boolean jiuzhi) {
        this.jiuzhi = jiuzhi;
    }

    public boolean isShisanlan() {
        return shisanlan;
    }

    public void setShisanlan(boolean shisanlan) {
        this.shisanlan = shisanlan;
    }

    public boolean isHongzhongbaiban() {
        return hongzhongbaiban;
    }

    public void setHongzhongbaiban(boolean hongzhongbaiban) {
        this.hongzhongbaiban = hongzhongbaiban;
    }

    public double getHuapai() {
        return huapai;
    }

    public void setHuapai(double huapai) {
        this.huapai = huapai;
    }

    public boolean isHaohuaqixiaodui1() {
        return haohuaqixiaodui1;
    }

    public void setHaohuaqixiaodui1(boolean haohuaqixiaodui1) {
        this.haohuaqixiaodui1 = haohuaqixiaodui1;
    }

    public boolean isHaohuaqixiaodui2() {
        return haohuaqixiaodui2;
    }

    public void setHaohuaqixiaodui2(boolean haohuaqixiaodui2) {
        this.haohuaqixiaodui2 = haohuaqixiaodui2;
    }
}
