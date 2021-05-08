package com.anbang.qipai.shouxianmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.pan.finish.CurrentPanFinishiDeterminer;

/**
 * 有人胡，并且所有人行牌结束或者牌库中没有牌结束
 */
public class ShouxianMajiangPanFinishiDeterminer implements CurrentPanFinishiDeterminer {
    /**
     * 局结束条件
     *
     * @param ju 当前局
     * @return
     */
    @Override
    public boolean determineToFinishCurrentPan(Ju ju) {
        Pan currentPan = ju.getCurrentPan();
        boolean hu = currentPan.anyPlayerHu();
        // 当有人胡并且所有人没有胡的动作游戏结束
        if (hu && currentPan.allPlayerHasNoHuActionCandidates()) {
            return true;
        } else {
            int liupai = 0;
            int avaliablePaiLeft = currentPan.countAvaliablePai(); //可用牌库
            if (avaliablePaiLeft <= liupai && currentPan.allPlayerHasNoHuActionCandidates()) { //流局
                return true;
            } else {
                return false;
            }
        }
    }
}
