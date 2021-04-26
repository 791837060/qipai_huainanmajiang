package com.dml.paodekuai.preparedapai.fapai;

import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.pan.Pan;
import com.dml.paodekuai.player.PaodekuaiPlayer;
import com.dml.puke.pai.PukePai;

import java.util.List;
import java.util.Map;

public class ShiwuzhangFapaiStrategy implements FapaiStrategy {

    @Override
    public void fapai(Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();//所有的四十八张牌
        Map<String, PaodekuaiPlayer> paodekuaiPlayerIdMajiangPlayerMap = currentPan
                .getPaodekuaiPlayerIdMajiangPlayerMap();

        //每个玩家发15张牌
        int times = 15;
        for (int i = 0; i<times; i ++) {
            for (PaodekuaiPlayer player : paodekuaiPlayerIdMajiangPlayerMap.values()) {
                PukePai pukePai = avaliablePaiList.remove(0);
                player.addShouPai(pukePai);
            }
        }
    }
}