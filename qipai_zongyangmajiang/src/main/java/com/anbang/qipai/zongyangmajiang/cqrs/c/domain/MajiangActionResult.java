package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import com.dml.majiang.pan.frame.PanActionFrame;

public class MajiangActionResult {

    private MajiangGameValueObject majiangGame;
    private PanActionFrame panActionFrame;
    private ZongyangMajiangPanResult panResult;
    private ZongyangMajiangJuResult juResult;

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

    public ZongyangMajiangPanResult getPanResult() {
        return panResult;
    }

    public void setPanResult(ZongyangMajiangPanResult panResult) {
        this.panResult = panResult;
    }

    public ZongyangMajiangJuResult getJuResult() {
        return juResult;
    }

    public void setJuResult(ZongyangMajiangJuResult juResult) {
        this.juResult = juResult;
    }

}
