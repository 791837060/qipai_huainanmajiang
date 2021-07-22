package com.anbang.qipai.huainanmajiang.cqrs.c.domain.test;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.pan.fapai.FaPaiStrategy;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.position.MajiangPosition;
import com.dml.majiang.position.MajiangPositionUtil;

import java.util.List;

/**
 * 顺序发牌
 */
public class ZongyangMajiangFaPaiStrategyTest implements FaPaiStrategy {
    /**
     * 每个玩家发牌数量
     */
    private int faPaiCountsForOnePlayer;

    public ZongyangMajiangFaPaiStrategyTest() {
    }

    public ZongyangMajiangFaPaiStrategyTest(int faPaiCountsForOnePlayer) {
        this.faPaiCountsForOnePlayer = faPaiCountsForOnePlayer;
    }

    /**
     * 发牌
     *
     * @param ju 当前局
     * @throws Exception
     */
    @Override
    public void faPai(Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        List<MajiangPai> avaliablePaiList = currentPan.getAvaliablePaiList();       //可用牌库
        MajiangPosition zhuangPlayerMenFeng = currentPan.findMenFengForZhuang();    //庄家门风
        MajiangPai[] pai = {
                MajiangPai.ertong,
                MajiangPai.ertong,
                MajiangPai.ertong,
                MajiangPai.santong,
                MajiangPai.santong,
                MajiangPai.santong,
                MajiangPai.sitong,
                MajiangPai.sitong,
                MajiangPai.sitong,
                MajiangPai.liutong,
                MajiangPai.qitong,
                MajiangPai.batong,
                MajiangPai.batong,
        };
        MajiangPai[] pai2 = {
                MajiangPai.yiwan,
                MajiangPai.erwan,
                MajiangPai.sanwan,
                MajiangPai.ertong,
                MajiangPai.santong,
                MajiangPai.sitong,
                MajiangPai.qitong,
                MajiangPai.qitong,
                MajiangPai.santiao,
                MajiangPai.sitiao,
                MajiangPai.batong,
                MajiangPai.batong,
                MajiangPai.batong,
        };
        MajiangPai[] pai3 = {
                MajiangPai.ertong,
                MajiangPai.ertong,
                MajiangPai.ertong,
                MajiangPai.santong,
                MajiangPai.santong,
                MajiangPai.santong,
                MajiangPai.sitong,
                MajiangPai.sitong,
                MajiangPai.batong,
                MajiangPai.liutong,
                MajiangPai.liutong,
                MajiangPai.batong,
                MajiangPai.batong,
        };
        for (int i = 0; i < faPaiCountsForOnePlayer; i++) {                         //从庄家开始依次发牌 直至到规定手牌
            MajiangPosition playerMenFeng = zhuangPlayerMenFeng;
            for (int j = 0; j < 4; j++) {
                MajiangPlayer player = currentPan.findPlayerByMenFeng(playerMenFeng);
                if (player != null) { //门风上有玩家才会发牌
                    if (player.getId().equals("286278") || player.getId().equals("972519")) {
                        player.addShoupai(pai[i]);
                    }else if(player.getId().equals("022285")){
                        player.addShoupai(pai2[i]);
                    }else {
                        faPai(avaliablePaiList, player);
                    }
                }
                playerMenFeng = MajiangPositionUtil.nextPositionAntiClockwise(playerMenFeng);
            }
        }
    }

    @Override
    public void faGudingPai(Ju ju,int a) throws Exception {
    }

    private void faPai(List<MajiangPai> avaliablePaiList, MajiangPlayer player) {
        MajiangPai pai = avaliablePaiList.remove(0);
        player.addShoupai(pai);
    }

    public int getFaPaiCountsForOnePlayer() {
        return faPaiCountsForOnePlayer;
    }

    public void setFaPaiCountsForOnePlayer(int faPaiCountsForOnePlayer) {
        this.faPaiCountsForOnePlayer = faPaiCountsForOnePlayer;
    }

}
