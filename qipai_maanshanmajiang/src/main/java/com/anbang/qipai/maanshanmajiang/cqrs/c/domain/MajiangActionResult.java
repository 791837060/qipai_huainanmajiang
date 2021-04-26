package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.pan.frame.PanActionFrame;

public class MajiangActionResult {

    private MajiangGameValueObject majiangGame;
    private PanActionFrame panActionFrame;
    private MaanshanMajiangPanResult panResult;
    private MaanshanMajiangJuResult juResult;

    public MajiangGameValueObject getMajiangGame() {
        return majiangGame;
    }

    public void setMajiangGame(MajiangGameValueObject majiangGame) {
        this.majiangGame = majiangGame;
    }

    public PanActionFrame getPanActionFrame() {
        return panActionFrame;
    }

    public void setPanActionFrame(PanActionFrame panActionFrame) {
        this.panActionFrame = panActionFrame;
    }

    public MaanshanMajiangPanResult getPanResult() {
        return panResult;
    }

    public void setPanResult(MaanshanMajiangPanResult panResult) {
        this.panResult = panResult;
    }

    public MaanshanMajiangJuResult getJuResult() {
        return juResult;
    }

    public void setJuResult(MaanshanMajiangJuResult juResult) {
        this.juResult = juResult;
    }

}
