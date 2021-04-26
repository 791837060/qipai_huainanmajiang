package com.anbang.qipai.doudizhu.cqrs.c.domain;

import com.anbang.qipai.doudizhu.cqrs.c.domain.qiangdizhu.QiangdizhuDizhuDeterminer;
import com.dml.doudizhu.gameprocess.CurrentPanFinishiDeterminer;
import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.player.DoudizhuPlayer;

/**
 * 有让牌
 * 
 * @author lsc
 *
 */
public class RangpaiCurrentPanFinishiDeterminer implements CurrentPanFinishiDeterminer {

	@Override
	public boolean determineToFinishCurrentPan(Ju ju) {
		Pan currentPan = ju.getCurrentPan();
		String dizhu = currentPan.getDizhuPlayerId();
		for (DoudizhuPlayer player : currentPan.getDoudizhuPlayerIdPlayerMap().values()) {
			if (player.getId().equals(dizhu)) {
				if (player.getAllShoupai().size() == 0) {
					currentPan.setYingjiaPlayerId(player.getId());
					return true;
				}
			} else {
				if (currentPan.getDoudizhuPlayerIdPlayerMap().size() == 2) {
					int rangpai = 0;
					if (player.getAllShoupai().size() <= rangpai) {
						currentPan.setYingjiaPlayerId(player.getId());
						return true;
					}
				}
			}
		}
		return false;
	}

}
