package com.anbang.qipai.paodekuai.cqrs.c.domain.test;

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
public class PaodekuaiFapaiStrategyTest implements FapaiStrategy {
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
        PukePaiMian[] paiMain = {
                PukePaiMian.fangkuaisan,
                PukePaiMian.heitaosi,
                PukePaiMian.heitaowu,
                PukePaiMian.hongxinwu,
                PukePaiMian.heitaoqi,
                PukePaiMian.heitaoba,
                PukePaiMian.hongxinba,
                PukePaiMian.meihuaba,
                PukePaiMian.fangkuaiba,
                PukePaiMian.fangkuaijiu,
                PukePaiMian.hongxinshi,
                PukePaiMian.meihuashi,
                PukePaiMian.meihuaJ,
                PukePaiMian.hongxinQ,
                PukePaiMian.hongxinK,
                PukePaiMian.meihuaK,
        };
        PukePaiMian[] paiMain2 = {
                PukePaiMian.heitaosan,
                PukePaiMian.hongxinsan,
                PukePaiMian.meihuasi,
                PukePaiMian.fangkuaisi,
                PukePaiMian.meihuawu,
                PukePaiMian.heitaoliu,
                PukePaiMian.hongxinliu,
                PukePaiMian.meihualiu,
                PukePaiMian.fangkuailiu,
                PukePaiMian.hongxinjiu,
                PukePaiMian.heitaoshi,
                PukePaiMian.fangkuaishi,
                PukePaiMian.heitaoQ,
                PukePaiMian.fangkuaiQ,
                PukePaiMian.fangkuaiK,
                PukePaiMian.fangkuaiA,
        };
        PukePaiMian[] paiMain3 = {
                PukePaiMian.heitaosan,
                PukePaiMian.fangkuaisan,
                PukePaiMian.meihuasi,
                PukePaiMian.hongxinwu,
                PukePaiMian.meihualiu,
                PukePaiMian.heitaoqi,
                PukePaiMian.meihuaqi,
                PukePaiMian.fangkuaiqi,
                PukePaiMian.meihuaba,
                PukePaiMian.heitaojiu,
                PukePaiMian.meihuaQ,
                PukePaiMian.meihuaK,
                PukePaiMian.hongxinA,
                PukePaiMian.meihuaA,
                PukePaiMian.fangkuaiA,
                PukePaiMian.heitaoer,
        };
        PukePaiMian[] paiMain4 = {
                PukePaiMian.heitaosan,
                PukePaiMian.hongxinsan,
                PukePaiMian.meihuasan,
                PukePaiMian.fangkuaisan,
                PukePaiMian.heitaoK,
                PukePaiMian.hongxinK,
                PukePaiMian.meihuaK,
                PukePaiMian.fangkuaiK,
                PukePaiMian.heitaowu,
                PukePaiMian.heitaoliu,
                PukePaiMian.heitaoqi,
                PukePaiMian.heitaoba,
                PukePaiMian.heitaojiu,
                PukePaiMian.heitaoshi,
                PukePaiMian.heitaoJ,
                PukePaiMian.heitaoQ,
        };
        for (int i = 0; i < times; i++) {
            for (PaodekuaiPlayer player : paodekuaiPlayerIdMajiangPlayerMap.values()) {
                if (player.getId().equals("395889") || player.getId().equals("031811")) {
                    if (player.getAllShoupai().size() > times) {
                        break;
                    }
                    PukePai pai = new PukePai();
                    pai.setId(paiMain[i].ordinal());
                    pai.setPaiMian(paiMain[i]);
                    player.addShouPai(pai);
                } else if (player.getId().equals("150636") || player.getId().equals("126102")) {
                    if (player.getAllShoupai().size() > times) {
                        break;
                    }
                    PukePai pai = new PukePai();
                    pai.setId(paiMain2[i].ordinal());
                    pai.setPaiMian(paiMain2[i]);
                    player.addShouPai(pai);
                } else {
                    PukePai pukePai = avaliablePaiList.remove(0);
                    player.addShouPai(pukePai);
                }

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
