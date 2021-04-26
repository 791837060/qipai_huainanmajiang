package com.anbang.qipai.tuidaohu.cqrs.c.domain;

import com.dml.majiang.player.Hu;
import com.dml.majiang.player.shoupai.ShoupaiPaiXing;

public class TuiDaoHuHu extends Hu {

    private TuiDaoHuHushu hufen;

    private boolean huxingHu;

    public TuiDaoHuHu() {
    }

    public TuiDaoHuHu(ShoupaiPaiXing shoupaiPaiXing, TuiDaoHuHushu hufen) {
        super(shoupaiPaiXing);
        this.hufen = hufen;
        this.huxingHu = true;
    }

    public TuiDaoHuHu(TuiDaoHuHushu hufen) {
        this.hufen = hufen;
        this.huxingHu = false;
    }

    public TuiDaoHuHushu getHufen() {
        return hufen;
    }

    public void setHufen(TuiDaoHuHushu hufen) {
        this.hufen = hufen;
    }

    public boolean isHuxingHu() {
        return huxingHu;
    }

    public void setHuxingHu(boolean huxingHu) {
        this.huxingHu = huxingHu;
    }
}
