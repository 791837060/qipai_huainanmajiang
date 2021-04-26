package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.action.mo.MajiangPlayerMoActionProcessor;
import com.dml.majiang.player.action.mo.QishouMopai;

public class MaanshanMajiangMoActionProcessor implements MajiangPlayerMoActionProcessor {

    @Override
    public void process(MajiangMoAction action, Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
//        MajiangPlayer player = currentPan.findPlayerById(action.getActionPlayerId());
        currentPan.playerMoPai(action.getActionPlayerId());

        if (action.getReason().getName().equals(QishouMopai.name)){
            currentPan.clearAllPlayersActionCandidates();
        }

    }

}
