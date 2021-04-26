package com.anbang.qipai.tuidaohu.cqrs.c.domain;

import com.dml.majiang.pan.result.PanPlayerResult;

public class TuiDaoHuPanPlayerResult extends PanPlayerResult {

    private TuiDaoHuHushu hufen;

    private TuiDaoHuGang gang;

    private TuidaohuNiao niao;
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

    public TuiDaoHuHushu getHufen() {
        return hufen;
    }

    public void setHufen(TuiDaoHuHushu hufen) {
        this.hufen = hufen;
    }

    public TuiDaoHuGang getGang() {
        return gang;
    }

    public void setGang(TuiDaoHuGang gang) {
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

    public TuidaohuNiao getNiao() {
        return niao;
    }

    public void setNiao(TuidaohuNiao niao) {
        this.niao = niao;
    }
}
