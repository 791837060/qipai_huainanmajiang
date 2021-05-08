package com.anbang.qipai.shouxianmajiang.cqrs.c.domain;

/**
 * 结算规则
 * 自摸：所有人跟胡家结算胡牌牌型分数；杠分单独结算
 * 放炮：其他玩家不用与胡家结算，只有放炮的人与胡家结算胡牌牌型分数；杠分单独结算
 */
public class ShouxianMajiangHushu {
    private boolean hu;                 //胡
    private boolean zimoHu;             //自摸胡；
//    private boolean tianhu;             //天胡；
//    private boolean dihu;               //地胡；

//    private boolean qingyise;           //清一色
    private boolean gangshangkaihua=false;    //杠上开花
//    private boolean qiangganghu;        //抢杠胡

//    private boolean qixiaodui;          //七小对
//    private boolean haohuaqixiaodui;    //豪华七小对
//    private boolean yitiaolong;         //一条龙
//    private boolean pengpenghu;         //碰碰胡
//
//    private boolean shisanyao;          //十三幺
    private boolean duyi=false;
    private boolean yaojiu=false;
    private boolean hongzhong=false;

    private int value;

    public void calculate() {
        int hushu = 0;
        if (hu || zimoHu) {
            hushu = 2;
        }
        if (gangshangkaihua){
            hushu*=2;
        }
        if (duyi){
            hushu*=2;
        }
        if (yaojiu){
            hushu*=2;
        }
        if (hongzhong){
            hushu*=2;
        }
//        if (tianhu || dihu) {
//            hushu = 9;
//        }
//        if (qingyise || qixiaodui || yitiaolong || pengpenghu) {
//            hushu = 9;
//        }
//        if (haohuaqixiaodui) {
//            hushu = 18;
//        }
//        if (shisanyao) {
//            hushu = 27;
//        }

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

    public boolean isGangshangkaihua() {
        return gangshangkaihua;
    }

    public void setGangshangkaihua(boolean gangshangkaihua) {
        this.gangshangkaihua = gangshangkaihua;
    }

    public boolean isDuyi() {
        return duyi;
    }

    public void setDuyi(boolean duyi) {
        this.duyi = duyi;
    }

    public boolean isYaojiu() {
        return yaojiu;
    }

    public void setYaojiu(boolean yaojiu) {
        this.yaojiu = yaojiu;
    }

    public boolean isHongzhong() {
        return hongzhong;
    }

    public void setHongzhong(boolean hongzhong) {
        this.hongzhong = hongzhong;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
