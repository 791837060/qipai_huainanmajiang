package com.anbang.qipai.tuidaohu.web.vo;

import com.anbang.qipai.tuidaohu.cqrs.c.domain.TuiDaoHuHushu;

public class TuiDaoHuHufenVO {
    private boolean tianhu;             //天胡；
    private boolean dihu;               //地胡；

    private boolean gangshangkaihua;    //杠上开花
    private boolean qiangganghu;        //抢杠胡
    private boolean qingyise;           //清一色

    private boolean qixiaodui;          //七小对
    private boolean haohuaqixiaodui;    //豪华七小对
    private boolean yitiaolong;         //一条龙
    private boolean pengpenghu;         //碰碰胡

    private boolean shisanyao;          //十三幺

    public TuiDaoHuHufenVO() {

    }

    public TuiDaoHuHufenVO(TuiDaoHuHushu hufen) {
        if (hufen.isDihu()) {
            dihu = true;
        } else if (hufen.isTianhu()) {
            tianhu = true;
        } else if (hufen.isQingyise()) {
            qingyise = true;
        } else if (hufen.isQiangganghu()) {
            qiangganghu = true;
        } else if (hufen.isGangshangkaihua()) {
            gangshangkaihua = true;
        } else if (hufen.isQixiaodui()) {
            qixiaodui = true;
        } else if (hufen.isHaohuaqixiaodui()) {
            haohuaqixiaodui = true;
        } else if (hufen.isYitiaolong()) {
            yitiaolong = true;
        } else if (hufen.isPengpenghu()) {
            pengpenghu = true;
        } else if (hufen.isShisanyao()) {
            shisanyao = true;
        }
    }

    public boolean isTianhu() {
        return tianhu;
    }

    public void setTianhu(boolean tianhu) {
        this.tianhu = tianhu;
    }

    public boolean isDihu() {
        return dihu;
    }

    public void setDihu(boolean dihu) {
        this.dihu = dihu;
    }

    public boolean isQingyise() {
        return qingyise;
    }

    public void setQingyise(boolean qingyise) {
        this.qingyise = qingyise;
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

    public boolean isShisanyao() {
        return shisanyao;
    }

    public void setShisanyao(boolean shisanyao) {
        this.shisanyao = shisanyao;
    }

    public boolean isPengpenghu() {
        return pengpenghu;
    }

    public void setPengpenghu(boolean pengpenghu) {
        this.pengpenghu = pengpenghu;
    }
}
