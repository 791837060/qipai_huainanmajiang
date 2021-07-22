package com.anbang.qipai.huainanmajiang.cqrs.c.domain;

import com.dml.majiang.player.Hu;
import com.dml.majiang.player.shoupai.ShoupaiPaiXing;

public class ZongyangMajiangHuHu extends Hu {

    private ZongyangMajiangHushu hufen;

    private boolean huxingHu;

    public ZongyangMajiangHuHu() {
    }

    public ZongyangMajiangHuHu(ShoupaiPaiXing shoupaiPaiXing, ZongyangMajiangHushu hufen) {
        super(shoupaiPaiXing);
        this.hufen = hufen;
        this.huxingHu = true;
    }

    public ZongyangMajiangHuHu(ZongyangMajiangHushu hufen) {
        this.hufen = hufen;
        this.huxingHu = false;
    }

    public ZongyangMajiangHushu getHufen() {
        return hufen;
    }

    public void setHufen(ZongyangMajiangHushu hufen) {
        this.hufen = hufen;
    }

    public boolean isHuxingHu() {
        return huxingHu;
    }

    public void setHuxingHu(boolean huxingHu) {
        this.huxingHu = huxingHu;
    }
}
