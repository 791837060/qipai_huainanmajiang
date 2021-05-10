package com.anbang.qipai.shouxianmajiang.web.vo;

import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.ShouxianMajiangHushu;

public class ShouxianMajiangHufenVO {
    private boolean gangshangkaihua = false;    //杠上开花
    private boolean duyi = false;
    private boolean yaojiu = false;
    private boolean hongzhong = false;

    public ShouxianMajiangHufenVO() {

    }

    public ShouxianMajiangHufenVO(ShouxianMajiangHushu hufen) {
        duyi = hufen.isDuyi();
        yaojiu = hufen.isYaojiu();
        hongzhong = hufen.isHongzhong();
        gangshangkaihua = hufen.isGangshangkaihua();
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

    public boolean isGangshangkaihua() {
        return gangshangkaihua;
    }

    public void setGangshangkaihua(boolean gangshangkaihua) {
        this.gangshangkaihua = gangshangkaihua;
    }
}
