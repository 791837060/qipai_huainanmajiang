package com.anbang.qipai.huainanmajiang.cqrs.c.domain;

import com.dml.majiang.pan.result.PanPlayerResult;

public class ZongyangMajiangPanPlayerResult extends PanPlayerResult {

    private ZongyangMajiangHushu hufen;

    private ZongyangMajiangGang gang;

    /**
     * 一盘的结算分
     */
    private Double score;
    /**
     * 可能为负数
     */
    private Double totalScore;

    private boolean tongpei;



    public boolean isTongpei() {
        return tongpei;
    }

    public void setTongpei(boolean tongpei) {
        this.tongpei = tongpei;
    }

    public ZongyangMajiangHushu getHufen() {
        return hufen;
    }

    public void setHufen(ZongyangMajiangHushu hufen) {
        this.hufen = hufen;
    }

    public ZongyangMajiangGang getGang() {
        return gang;
    }

    public void setGang(ZongyangMajiangGang gang) {
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
}
