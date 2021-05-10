package com.anbang.qipai.biji.cqrs.c.domain.test;

import com.dml.shisanshui.ju.Ju;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.PukePaiMian;
import com.dml.shisanshui.pan.Pan;
import com.dml.shisanshui.player.ShisanshuiPlayer;
import com.dml.shisanshui.preparedapai.fapai.FapaiStrategy;

import java.util.List;
import java.util.Map;


public class EveryPlayerShisanzhangFapaiStrategyTest implements FapaiStrategy {

    @Override
    public void fapai(Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();
        Map<String, ShisanshuiPlayer> playerIdPlayerMap = currentPan.getPlayerIdPlayerMap();

        int[] paiId = {3, 7, 11, 15, 19, 23, 29, 36, 46};
        PukePaiMian[] paiMain = {
                PukePaiMian.meihuaA,
                PukePaiMian.meihuaer,
                PukePaiMian.hongxinsan,
                PukePaiMian.fangkuaisi,
                PukePaiMian.heitaowu,
                PukePaiMian.hongxinliu,
                PukePaiMian.meihuaqi,
                PukePaiMian.meihuaba,
                PukePaiMian.meihuajiu
        };

        for (int i = 0; i < 9; i++) {
            for (ShisanshuiPlayer player : playerIdPlayerMap.values()) {
                if (player.getId().equals("730037")) {
                    PukePai pai = new PukePai();
                    pai.setId(paiId[i]);
                    pai.setPaiMian(paiMain[i]);
                    player.addShoupai(pai);
                } else {
                    PukePai pukePai = avaliablePaiList.remove(0);
                    player.addShoupai(pukePai);
                }
            }
        }
        currentPan.setAvaliablePaiList(avaliablePaiList);
    }

}
