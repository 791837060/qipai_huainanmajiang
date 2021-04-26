package com.dml.shuangkou.pai.jiesuanpai;

import com.dml.puke.pai.DianShu;

public class HongxinErDangPai extends ShoupaiJiesuanPai {

    private String dangType = "HongxinErDang";

    private DianShu[] yuanPai;

    private DianShu dangPai;

    public HongxinErDangPai() {

    }

    public HongxinErDangPai(DianShu dangPai) {
        yuanPai = new DianShu[]{DianShu.hongxiner2};
        this.dangPai = dangPai;
    }

    @Override
    public String dangType() {
        return dangType;
    }

    @Override
    public DianShu[] getAllYuanPai() {
        return yuanPai;
    }

    public String getDangType() {
        return dangType;
    }

    public void setDangType(String dangType) {
        this.dangType = dangType;
    }

    public DianShu[] getYuanPai() {
        return yuanPai;
    }

    public void setYuanPai(DianShu[] yuanPai) {
        this.yuanPai = yuanPai;
    }

    public DianShu getDangPai() {
        return dangPai;
    }

    public void setDangPai(DianShu dangPai) {
        this.dangPai = dangPai;
    }

    @Override
    public DianShu getDangPaiType() {
        return dangPai;
    }

}
