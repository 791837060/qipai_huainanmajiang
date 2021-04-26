package com.anbang.qipai.maanshanmajiang.cqrs.c.domain.test;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.OptionalPlay;
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
public class MaanshanMajiangFaPaiStrategyTest implements FaPaiStrategy {
    /**
     * 每个玩家发牌数量
     */
    private int faPaiCountsForOnePlayer;

    private OptionalPlay optionalPlay;

    public MaanshanMajiangFaPaiStrategyTest() {
    }

    public MaanshanMajiangFaPaiStrategyTest(int faPaiCountsForOnePlayer) {
        this.faPaiCountsForOnePlayer = faPaiCountsForOnePlayer;
    }

    public MaanshanMajiangFaPaiStrategyTest(int faPaiCountsForOnePlayer, OptionalPlay optionalPlay) {
        this.faPaiCountsForOnePlayer = faPaiCountsForOnePlayer;
        this.optionalPlay = optionalPlay;
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
                MajiangPai.yiwan,
                MajiangPai.yiwan,
                MajiangPai.yiwan,
                MajiangPai.sanwan,
                MajiangPai.sanwan,
                MajiangPai.sanwan,
                MajiangPai.wuwan,
                MajiangPai.wuwan,
                MajiangPai.wuwan,
                MajiangPai.yitiao,
                MajiangPai.ertiao,
                MajiangPai.santiao,
                MajiangPai.sitiao,
        };
        MajiangPai[] pai2 = {
                MajiangPai.yitong,
                MajiangPai.yitong,
                MajiangPai.yitong,
                MajiangPai.santong,
                MajiangPai.santong,
                MajiangPai.santong,
                MajiangPai.wutong,
                MajiangPai.wutong,
                MajiangPai.wutong,
                MajiangPai.sitiao,
                MajiangPai.wutiao,
                MajiangPai.liutiao,
                MajiangPai.qitiao,
        };
        for (int i = 0; i < faPaiCountsForOnePlayer; i++) { //从庄家开始依次发牌 直至到规定手牌
            MajiangPosition playerMenFeng = zhuangPlayerMenFeng;
            for (int j = 0; j < 4; j++) {
                MajiangPlayer player = currentPan.findPlayerByMenFeng(playerMenFeng);
                if (player != null) { //门风上有玩家才会发牌
                    if (player.getId().equals("237129") || player.getId().equals("960973")) {
                        player.addShoupai(pai[i]);
                    } else if (player.getId().equals("830559") || player.getId().equals("713072")) {
                        player.addShoupai(pai2[i]);
                    } else {
                        faPai(avaliablePaiList, player);
                    }
                }
                playerMenFeng = MajiangPositionUtil.nextPositionAntiClockwise(playerMenFeng);
            }
        }
    }

    @Override
    public void faGudingPai(Ju ju, int a) throws Exception {
    }

    private void faPai(List<MajiangPai> avaliablePaiList, MajiangPlayer player) {
        MajiangPai pai = avaliablePaiList.remove(0);
        player.addShoupai(pai);
    }

    private void faPai(List<MajiangPai> avaliablePaiList, MajiangPlayer player, MajiangPai huapai) {
        MajiangPai pai = avaliablePaiList.remove(0);
        if (pai.ordinal() >= huapai.ordinal()) {
            player.addPublicPai(pai);
            faPai(avaliablePaiList, player, huapai);
        } else {
            player.addShoupai(pai);
        }
    }

    public int getFaPaiCountsForOnePlayer() {
        return faPaiCountsForOnePlayer;
    }

    public void setFaPaiCountsForOnePlayer(int faPaiCountsForOnePlayer) {
        this.faPaiCountsForOnePlayer = faPaiCountsForOnePlayer;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
