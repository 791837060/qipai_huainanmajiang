package com.anbang.qipai.paodekuai.cqrs.c.domain;

import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.pan.Pan;
import com.dml.paodekuai.player.PaodekuaiPlayer;
import com.dml.paodekuai.preparedapai.fapai.FapaiStrategy;
import com.dml.paodekuai.wanfa.OptionalPlay;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;

import java.util.List;
import java.util.Map;

/**
 * @Description: 2-3人时各玩家手牌数一致，直接发牌
 */
public class PaodekuaiFapaiStrategy implements FapaiStrategy {
    private OptionalPlay optionalPlay;

    @Override
    public void fapai(Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();//所有的四十八张牌
        Map<String, PaodekuaiPlayer> paodekuaiPlayerIdMajiangPlayerMap = currentPan.getPaodekuaiPlayerIdMajiangPlayerMap();
        int times = 0;
        //每个玩家发16张牌
        if (optionalPlay.isShiwuzhang()) {
            times = 15;
        } else if (optionalPlay.isShiliuzhang()) {
            times = 16;
        } else {
            times = 17;
        }

        for (int i = 0; i < times; i++) {
            for (PaodekuaiPlayer player : paodekuaiPlayerIdMajiangPlayerMap.values()) {
                PukePai pukePai = avaliablePaiList.remove(0);
                player.addShouPai(pukePai);
            }
        }

    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
