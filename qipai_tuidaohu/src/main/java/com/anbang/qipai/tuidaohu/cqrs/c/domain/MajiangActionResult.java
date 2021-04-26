package com.anbang.qipai.tuidaohu.cqrs.c.domain;

import com.dml.majiang.pan.frame.PanActionFrame;

public class MajiangActionResult {

    private MajiangGameValueObject majiangGame;
    private PanActionFrame panActionFrame;
    private TuiDaoHuPanResult panResult;
    private TuiDaoHuJuResult juResult;

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

    public TuiDaoHuPanResult getPanResult() {
        return panResult;
    }

    public void setPanResult(TuiDaoHuPanResult panResult) {
        this.panResult = panResult;
    }

    public TuiDaoHuJuResult getJuResult() {
        return juResult;
    }

    public void setJuResult(TuiDaoHuJuResult juResult) {
        this.juResult = juResult;
    }

}
