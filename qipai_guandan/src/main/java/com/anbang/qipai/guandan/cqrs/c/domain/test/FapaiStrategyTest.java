package com.anbang.qipai.guandan.cqrs.c.domain.test;

import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.HuaSe;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.player.ShuangkouPlayer;
import com.dml.shuangkou.preparedapai.fapai.FapaiStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 每人每次发9张
 *
 * @author lsc
 */
public class FapaiStrategyTest implements FapaiStrategy {

    @Override
    public void fapai(Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        List<String> playerIds = currentPan.findAllPlayerId();
        List<PukePai> remainPaiList = new ArrayList<>();
        List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();
        Map<String, ShuangkouPlayer> shuangkouPlayerIdMajiangPlayerMap = currentPan.getShuangkouPlayerIdPlayerMap();

        PukePaiMian[] paiMain = {
                PukePaiMian.heitaosan,
                PukePaiMian.heitaosi,
                PukePaiMian.heitaowu,
                PukePaiMian.heitaoliu,
                PukePaiMian.heitaoqi,
                PukePaiMian.heitaojiu,
                PukePaiMian.heitaoshi,
                PukePaiMian.heitaoJ,
                PukePaiMian.heitaoQ,
                PukePaiMian.heitaoK,
                PukePaiMian.hongxinK,
                PukePaiMian.meihuaK,
                PukePaiMian.fangkuaiK,
                PukePaiMian.heitaoA,
                PukePaiMian.hongxinA,
                PukePaiMian.meihuaA,
                PukePaiMian.fangkuaiA,
                PukePaiMian.hongxinsan,
                PukePaiMian.meihuasan,
                PukePaiMian.fangkuaisan,
                PukePaiMian.hongxinsi,
        };


        if (playerIds.size() > 2) {// 4人
            for (ShuangkouPlayer player : shuangkouPlayerIdMajiangPlayerMap.values()) {
                if (player.getId().equals("957987") || player.getId().equals("809538") || player.getId().equals("861018") || player.getId().equals("218150")) {
                    for (int j = 0; j < paiMain.length; j++) {
                        PukePai pai = new PukePai();
                        pai.setId(paiMain[j].ordinal());
                        pai.setPaiMian(paiMain[j]);
                        player.addShouPai(pai);
                    }
                    PukePai pai = new PukePai();
                    pai.setId(97);
                    pai.setPaiMian(PukePaiMian.heitaoer);
                    player.addShouPai(pai);

                    PukePai pai5 = new PukePai();
                    pai5.setId(99);
                    pai5.setPaiMian(PukePaiMian.hongxiner2);
                    player.addShouPai(pai5);

                    PukePai pai1 = new PukePai();
                    pai1.setId(104);
                    pai1.setPaiMian(PukePaiMian.xiaowang);
                    player.addShouPai(pai1);

                    PukePai pai2 = new PukePai();
                    pai2.setId(105);
                    pai2.setPaiMian(PukePaiMian.xiaowang);
                    player.addShouPai(pai2);

                    PukePai pai3 = new PukePai();
                    pai3.setId(106);
                    pai3.setPaiMian(PukePaiMian.dawang);
                    player.addShouPai(pai3);

                    PukePai pai4 = new PukePai();
                    pai4.setId(107);
                    pai4.setPaiMian(PukePaiMian.dawang);
                    player.addShouPai(pai4);
                } else {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 9; j++) {
                            PukePai pukePai = avaliablePaiList.remove(0);
                            if (pukePai.getPaiMian().dianShu().equals(DianShu.er) && pukePai.getPaiMian().huaSe().equals(HuaSe.hongxin)) {
                                PukePai hongxinEr = new PukePai();
                                hongxinEr.setId(pukePai.getId());
                                hongxinEr.setPaiMian(PukePaiMian.hongxiner2);
                                player.addShouPai(hongxinEr);
                            } else {
                                player.addShouPai(pukePai);
                            }

                        }
                    }

                }
            }
        } else {// 2人
            for (String playerId : playerIds) {
                ShuangkouPlayer player = shuangkouPlayerIdMajiangPlayerMap.get(playerId);
                if (player.getId().equals("150636") || !player.getId().equals("395889")) {
                    for (int j = 0; j < paiMain.length; j++) {
                        PukePai pai = new PukePai();
                        pai.setId(paiMain[j].ordinal());
                        pai.setPaiMian(paiMain[j]);
                        player.addShouPai(pai);
                    }
                    PukePai pai = new PukePai();
                    pai.setId(97);
                    pai.setPaiMian(PukePaiMian.heitaoer);
                    player.addShouPai(pai);

                    PukePai pai5 = new PukePai();
                    pai5.setId(99);
                    pai5.setPaiMian(PukePaiMian.hongxiner2);
                    player.addShouPai(pai5);

                    PukePai pai1 = new PukePai();
                    pai1.setId(104);
                    pai1.setPaiMian(PukePaiMian.xiaowang);
                    player.addShouPai(pai1);

                    PukePai pai2 = new PukePai();
                    pai2.setId(105);
                    pai2.setPaiMian(PukePaiMian.xiaowang);
                    player.addShouPai(pai2);

                    PukePai pai3 = new PukePai();
                    pai3.setId(106);
                    pai3.setPaiMian(PukePaiMian.dawang);
                    player.addShouPai(pai3);

                    PukePai pai4 = new PukePai();
                    pai4.setId(107);
                    pai4.setPaiMian(PukePaiMian.dawang);
                    player.addShouPai(pai4);
                } else {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 9; j++) {
                            PukePai pukePai = avaliablePaiList.remove(0);
                            if (pukePai.getPaiMian().dianShu().equals(DianShu.er) && pukePai.getPaiMian().huaSe().equals(HuaSe.hongxin)) {
                                PukePai hongxinEr = new PukePai();
                                hongxinEr.setId(pukePai.getId());
                                hongxinEr.setPaiMian(PukePaiMian.hongxiner2);
                                player.addShouPai(hongxinEr);
                            } else {
                                player.addShouPai(pukePai);
                            }

                        }
                        for (int j = 0; j < 9; j++) {
                            PukePai pukePai = avaliablePaiList.remove(0);
                            remainPaiList.add(pukePai);
                        }
                    }
                }

            }
        }
        avaliablePaiList.addAll(remainPaiList);

    }

}
