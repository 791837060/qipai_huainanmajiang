package com.dml.paodekuai.gameprocess;

import java.util.List;

import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.pan.Pan;

/**
 * 任一玩家打光手牌，一盘结束
 */
public class OnePlayerNoPaiPanFinishiDeterminer implements CurrentPanFinishiDeterminer {

	@Override
	public boolean determineToFinishCurrentPan(Ju ju) {
		Pan currentPan = ju.getCurrentPan();
		List<String> playerIds = currentPan.getNoPaiPlayerIdList();
		if (playerIds.size() > 0) {
			return true;
		}
		return false;
	}

}
