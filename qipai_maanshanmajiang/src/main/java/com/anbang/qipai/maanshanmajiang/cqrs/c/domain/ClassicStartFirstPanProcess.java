package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.ju.firstpan.StartFirstPanProcess;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;

import java.util.List;

public class ClassicStartFirstPanProcess implements StartFirstPanProcess {

    @Override
    public void startFirstPan(Ju ju, List<String> allPlayerIds) throws Exception {
        Pan firstPan = new Pan();
        firstPan.setNo(1);
        firstPan.setDaoNewPan(true);
        allPlayerIds.forEach(firstPan::addPlayer);
        ju.setCurrentPan(firstPan);

        // 开始定第一盘的门风
        ju.determinePlayersMenFengForFirstPan();

        // 开始定第一盘庄家
        ju.determineZhuangForFirstPan();

        // 开始填充可用的牌
        ju.fillAvaliablePai();

        // 开始定财神
//		ju.determineGuipai();

        // 开始定搬子和配子
//        ju.determineBanziAndPeizi();

        // 开始发牌
        ju.faPai();

        // 庄家可以摸第一张牌
        ju.updateInitialAction();

        for (MajiangPlayer majiangPlayer : ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().values()) {
            majiangPlayer.setSuggestQuemen(majiangPlayer.calculateQuemen());
        }

        // 庄家摸第一张牌,进入正式行牌流程
        ju.action(ju.getCurrentPan().getZhuangPlayerId(), 1, 0, System.currentTimeMillis());
    }

}
