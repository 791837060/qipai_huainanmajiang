package com.dml.doudizhu.gameprocess;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.player.DoudizhuPlayer;

/**
 * 有一个玩家出完牌就结束
 * 
 * @author lsc
 *
 */
public class OnePlayerHasNoPaiPanFinishiDeterminer implements CurrentPanFinishiDeterminer {

	@Override
	public boolean determineToFinishCurrentPan(Ju ju) {
		Pan currentPan = ju.getCurrentPan();
		for (DoudizhuPlayer player : currentPan.getDoudizhuPlayerIdPlayerMap().values()) {
			if (player.getAllShoupai().size() == 0) {
				currentPan.setYingjiaPlayerId(player.getId());
				return true;
			}
		}
		return false;
	}

}
