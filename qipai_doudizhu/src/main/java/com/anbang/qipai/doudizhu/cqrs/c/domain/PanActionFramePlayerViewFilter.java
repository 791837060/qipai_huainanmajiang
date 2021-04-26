package com.anbang.qipai.doudizhu.cqrs.c.domain;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import com.dml.doudizhu.pan.PanActionFrame;
import com.dml.doudizhu.pan.PanValueObject;
import com.dml.doudizhu.player.DoudizhuPlayerValueObject;

public class PanActionFramePlayerViewFilter {

	public PanActionFrame filter(GameLatestPanActionFrameDbo frame, String playerId) {
		PanValueObject pan = frame.getPanActionFrame().getPanAfterAction();
		pan.getPaiListValueObject().setPaiList(null);
		for (DoudizhuPlayerValueObject player : pan.getDoudizhuPlayerList()) {
			if (player.getId().equals(playerId)) {// 是自己
				// 什么都不过滤，全要看
			} else {// 是其他玩家
				player.setAllShoupai(null);
				player.setShoupaiDianShuAmountArray(null);
				player.setShoupaiIdListForSortList(null);
				player.setYaPaiSolutionCandidates(null);
				player.setYaPaiSolutionsForTips(null);
			}
		}
		return frame.getPanActionFrame();
	}
}
