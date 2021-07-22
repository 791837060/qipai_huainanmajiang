package com.anbang.qipai.huainanmajiang.cqrs.c.domain;

import com.dml.majiang.player.shoupai.GuipaiDangPai;

/**
 * 结算规则
 * 自摸：所有人跟胡家结算胡牌牌型分数；杠分单独结算
 * 放炮：其他玩家不用与胡家结算，只有放炮的人与胡家结算胡牌牌型分数；杠分单独结算
 */
public class ZongyangMajiangHushu {
    private boolean hu;                 //胡
    private boolean zimoHu;             //自摸胡；
    private boolean qingyise;           //清一色
    private boolean hunyise;
    private boolean gangshangkaihua;    //杠上开花
    private boolean qiangganghu;        //抢杠胡
    private boolean qixiaodui;          //七小对
    private boolean haohuaqixiaodui;    //豪华七小对
    private boolean yitiaolong;         //一条龙
    private boolean pengpenghu;         //碰碰胡
    private int value;

    public void calculate() {
        int hushu = 0;
        if (hu || zimoHu) {
            hushu = 2;
        }
        if (qingyise){
            hushu = 20;
        }
        if (yitiaolong) {
            hushu = 15;
        }
        if (haohuaqixiaodui) {
            hushu = 18;
        }
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

    public boolean isPengpenghu() {
        return pengpenghu;
    }

    public void setPengpenghu(boolean pengpenghu) {
        this.pengpenghu = pengpenghu;
    }
}
