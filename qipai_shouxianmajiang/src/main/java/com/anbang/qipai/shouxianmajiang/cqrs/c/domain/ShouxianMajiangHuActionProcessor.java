package com.anbang.qipai.shouxianmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.Hu;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.hu.MajiangHuAction;
import com.dml.majiang.player.action.hu.MajiangPlayerHuActionProcessor;

/**
 * 胡牌
 */
public class ShouxianMajiangHuActionProcessor implements MajiangPlayerHuActionProcessor {

    @Override
    public void process(MajiangHuAction action, Ju ju) throws Exception {
        Hu hu = action.getHu();
        Pan currentPan = ju.getCurrentPan();
        MajiangPlayer huPlayer = currentPan.findPlayerById(action.getActionPlayerId());
        huPlayer.setHu(hu);
        huPlayer.clearActionCandidates();
    }

}
