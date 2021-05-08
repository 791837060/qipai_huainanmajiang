package com.anbang.qipai.shouxianmajiang.cqrs.c.domain;

import com.dml.majiang.pan.result.PanPlayerResult;

public class ShouxianMajiangPanPlayerResult extends PanPlayerResult {

    private ShouxianMajiangHushu hufen;

    private ShouxianMajiangGang gang;

    private int xiapaoCount;

    private int paofen;
    /**
     * 一盘的结算分
     */
    private Double score;
    /**
     * 可能为负数
     */
    private Double totalScore;



    public ShouxianMajiangHushu getHufen() {
        return hufen;
    }

    public void setHufen(ShouxianMajiangHushu hufen) {
        this.hufen = hufen;
    }

    public ShouxianMajiangGang getGang() {
        return gang;
    }

    public void setGang(ShouxianMajiangGang gang) {
        this.gang = gang;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public int getXiapaoCount() {
        return xiapaoCount;
    }

    public void setXiapaoCount(int xiapaoCount) {
        this.xiapaoCount = xiapaoCount;
    }

    public int getPaofen() {
        return paofen;
    }

    public void setPaofen(int paofen) {
        this.paofen = paofen;
    }
}
