package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.player.Hu;
import com.dml.majiang.player.shoupai.ShoupaiPaiXing;

public class MaanshanMajiangHu extends Hu {

    private MaanshanMajiangHushu hufen;

    private boolean huxingHu;

    public MaanshanMajiangHu() {
    }

    public MaanshanMajiangHu(ShoupaiPaiXing shoupaiPaiXing, MaanshanMajiangHushu hufen) {
        super(shoupaiPaiXing);
        this.hufen = hufen;
        this.huxingHu = true;
    }

    public MaanshanMajiangHu(MaanshanMajiangHushu hufen) {
        this.hufen = hufen;
        this.huxingHu = false;
    }

    public MaanshanMajiangHushu getHufen() {
        return hufen;
    }

    public void setHufen(MaanshanMajiangHushu hufen) {
        this.hufen = hufen;
    }

    public boolean isHuxingHu() {
        return huxingHu;
    }

    public void setHuxingHu(boolean huxingHu) {
        this.huxingHu = huxingHu;
    }
}
