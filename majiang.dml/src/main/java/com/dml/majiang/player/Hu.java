package com.dml.majiang.player;

import com.dml.majiang.player.shoupai.ShoupaiPaiXing;

public abstract class Hu {
    private ShoupaiPaiXing shoupaiPaiXing;
    private boolean zimo;

    private boolean dianpao;
    private String dianpaoPlayerId;

    private boolean qianggang;

    public Hu() {
    }

    public Hu(ShoupaiPaiXing shoupaiPaiXing) {
        this.shoupaiPaiXing = shoupaiPaiXing;
    }

    public ShoupaiPaiXing getShoupaiPaiXing() {
        return shoupaiPaiXing;
    }

    public void setShoupaiPaiXing(ShoupaiPaiXing shoupaiPaiXing) {
        this.shoupaiPaiXing = shoupaiPaiXing;
    }

    public boolean isZimo() {
        return zimo;
    }

    public void setZimo(boolean zimo) {
        this.zimo = zimo;
    }

    public boolean isDianpao() {
        return dianpao;
    }

    public void setDianpao(boolean dianpao) {
        this.dianpao = dianpao;
    }

    public String getDianpaoPlayerId() {
        return dianpaoPlayerId;
    }

    public void setDianpaoPlayerId(String dianpaoPlayerId) {
        this.dianpaoPlayerId = dianpaoPlayerId;
    }

    public boolean isQianggang() {
        return qianggang;
    }

    public void setQianggang(boolean qianggang) {
        this.qianggang = qianggang;
    }

}
