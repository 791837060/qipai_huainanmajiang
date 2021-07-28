package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.action.mo.MajiangPlayerMoActionProcessor;

public class ZongyangMajiangMoActionProcessor implements MajiangPlayerMoActionProcessor {

    @Override
    public void process(MajiangMoAction action, Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        currentPan.playerMoPai(action.getActionPlayerId());
        MajiangPlayer player = currentPan.findPlayerById(action.getActionPlayerId());
        if (action.getReason().getName().equals(ZongyangMajiangBupai.name)) {
            player.fangruPublicPai();
        }
        currentPan.playerMoPai(action.getActionPlayerId());
    }

}
