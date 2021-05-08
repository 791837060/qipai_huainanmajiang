package com.anbang.qipai.shouxianmajiang.cqrs.c.domain;

import com.dml.majiang.pan.frame.PanActionFrame;

public class MajiangActionResult {

    private MajiangGameValueObject majiangGame;
    private PanActionFrame panActionFrame;
    private ShouxianMajiangPanResult panResult;
    private ShouxianMajiangJuResult juResult;

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

    public ShouxianMajiangPanResult getPanResult() {
        return panResult;
    }

    public void setPanResult(ShouxianMajiangPanResult panResult) {
        this.panResult = panResult;
    }

    public ShouxianMajiangJuResult getJuResult() {
        return juResult;
    }

    public void setJuResult(ShouxianMajiangJuResult juResult) {
        this.juResult = juResult;
    }

}
