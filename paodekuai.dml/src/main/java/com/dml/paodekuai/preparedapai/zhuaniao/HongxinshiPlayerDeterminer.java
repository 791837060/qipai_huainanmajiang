package com.dml.paodekuai.preparedapai.zhuaniao;

import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.pan.Pan;
import com.dml.paodekuai.player.PaodekuaiPlayer;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;

/**
 * @Description: 红桃10抓鸟
 */
public class HongxinshiPlayerDeterminer implements ZhuaniaoPlayerDeterminer {
    private boolean zhuaniao;

    @Override
    public void determineXiandaPlayer(Ju ju) {
        if (!zhuaniao) {
            return ;
        }

        Pan currentPan = ju.getCurrentPan();
        for (PaodekuaiPlayer player : currentPan.getPaodekuaiPlayerIdMajiangPlayerMap().values()) {
            for (PukePai pukePai : player.getAllShoupai().values()) {
                //红心十鸟牌标记
                if (pukePai.getPaiMian().equals(PukePaiMian.hongxinshi)) {
                    pukePai.setMark(new NiaoPaiMark());
                    String zhuaniaoPlayerId = player.getId();
                    currentPan.setZhuaniaoPlayerId(zhuaniaoPlayerId);
                }
            }
        }
    }

    public HongxinshiPlayerDeterminer() {

    }

    public HongxinshiPlayerDeterminer(boolean zhuaniao) {
        this.zhuaniao = zhuaniao;
    }

    public boolean isZhuaniao() {
        return zhuaniao;
    }

    public void setZhuaniao(boolean zhuaniao) {
        this.zhuaniao = zhuaniao;
    }
}
