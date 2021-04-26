package com.dml.majiang.player.action.initial;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.action.mo.QishouMopai;

public class ZhuangMoPaiInitialActionUpdater implements MajiangPlayerInitialActionUpdater {

	@Override
	public void updateActions(Ju ju) {
		Pan currentPan = ju.getCurrentPan();
		currentPan.addPlayerActionCandidate(new MajiangMoAction(currentPan.getZhuangPlayerId(), new QishouMopai()));
		currentPan.updatePublicWaitingPlayerIdToZhuang();
	}

}
