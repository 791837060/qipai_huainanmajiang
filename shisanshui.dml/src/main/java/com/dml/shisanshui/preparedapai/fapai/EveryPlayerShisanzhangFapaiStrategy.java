package com.dml.shisanshui.preparedapai.fapai;

import java.util.List;
import java.util.Map;

import com.dml.shisanshui.ju.Ju;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pan.Pan;
import com.dml.shisanshui.player.ShisanshuiPlayer;


public class EveryPlayerShisanzhangFapaiStrategy implements FapaiStrategy {

    @Override
    public void fapai(Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();
        Map<String, ShisanshuiPlayer> playerIdPlayerMap = currentPan.getPlayerIdPlayerMap();
        for (int i = 0; i < 9; i++) {
            for (ShisanshuiPlayer player : playerIdPlayerMap.values()) {
                PukePai pukePai = avaliablePaiList.remove(0);
                player.addShoupai(pukePai);
            }
        }
        currentPan.setAvaliablePaiList(avaliablePaiList);
    }

}
