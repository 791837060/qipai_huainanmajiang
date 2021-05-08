package com.anbang.qipai.shouxianmajiang.cqrs.c.domain;

import com.dml.majiang.player.Hu;
import com.dml.majiang.player.shoupai.ShoupaiPaiXing;

public class ShouxianMajiangHu extends Hu {

    private ShouxianMajiangHushu hufen;

    private boolean huxingHu;

    public ShouxianMajiangHu() {
    }

    public ShouxianMajiangHu(ShoupaiPaiXing shoupaiPaiXing, ShouxianMajiangHushu hufen) {
        super(shoupaiPaiXing);
        this.hufen = hufen;
        this.huxingHu = true;
    }

    public ShouxianMajiangHu(ShouxianMajiangHushu hufen) {
        this.hufen = hufen;
        this.huxingHu = false;
    }

    public ShouxianMajiangHushu getHufen() {
        return hufen;
    }

    public void setHufen(ShouxianMajiangHushu hufen) {
        this.hufen = hufen;
    }

    public boolean isHuxingHu() {
        return huxingHu;
    }

    public void setHuxingHu(boolean huxingHu) {
        this.huxingHu = huxingHu;
    }
}
