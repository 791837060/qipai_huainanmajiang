package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.pan.result.PanPlayerResult;

public class MaanshanMajiangPanPlayerResult extends PanPlayerResult {
    /**
     * 胡分
     */
    private MaanshanMajiangHushu hufen;
    /**
     * 杠
     */
    private MaanshanMajiangGang gang;
    /**
     * 一盘的结算分
     */
    private Double score;
    /**
     * 可能为负数
     */
    private Double totalScore;
    /**
     * 体外循环分数
     */
    private Double tiwaixunhuanScore;

    public MaanshanMajiangHushu getHufen() {
        return hufen;
    }

    public void setHufen(MaanshanMajiangHushu hufen) {
        this.hufen = hufen;
    }

    public MaanshanMajiangGang getGang() {
        return gang;
    }

    public void setGang(MaanshanMajiangGang gang) {
        this.gang = gang;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getTiwaixunhuanScore() {
        return tiwaixunhuanScore;
    }

    public void setTiwaixunhuanScore(Double tiwaixunhuanScore) {
        this.tiwaixunhuanScore = tiwaixunhuanScore;
    }
}
