package com.dml.shisanshui.gameprocess;

import com.dml.shisanshui.ju.Ju;
import com.dml.shisanshui.pan.Pan;
import com.dml.shisanshui.player.ShisanshuiPlayer;

/**
 * 所有人出完牌
 * 
 * @author lsc
 *
 */
public class AllPlayerChupaiPanFinishiDeterminer implements CurrentPanFinishiDeterminer {

	@Override
	public boolean determineToFinishCurrentPan(Ju ju) {
		Pan currentPan = ju.getCurrentPan();
		for (ShisanshuiPlayer player : currentPan.getPlayerIdPlayerMap().values()) {
			if (player.getChupaiSolution() == null) {
				return false;
			}
		}
		return true;
	}

}
