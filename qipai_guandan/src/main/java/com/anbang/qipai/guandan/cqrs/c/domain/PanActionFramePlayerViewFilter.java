package com.anbang.qipai.guandan.cqrs.c.domain;

import com.anbang.qipai.guandan.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import com.dml.puke.wanfa.position.Position;
import com.dml.puke.wanfa.position.PositionUtil;
import com.dml.shuangkou.pan.PanActionFrame;
import com.dml.shuangkou.pan.PanValueObject;
import com.dml.shuangkou.player.ShuangkouPlayerValueObject;

/**
 * 双明的过滤
 * 
 * @author lsc
 *
 */
public class PanActionFramePlayerViewFilter {
	public PanActionFrame filter(GameLatestPanActionFrameDbo frame, String playerId, boolean shuangming) {

		PanValueObject pan = frame.getPanActionFrame().getPanAfterAction();
		pan.getPaiListValueObject().setPaiList(null);
		Position duijiaPosition = null;
		ShuangkouPlayerValueObject filterPlayer = null;
		for (ShuangkouPlayerValueObject player : pan.getShuangkouPlayerList()) {
			if (player.getId().equals(playerId)) {// 是自己
				filterPlayer = player;
				duijiaPosition = PositionUtil.nextPositionClockwise(player.getPosition());
				duijiaPosition = PositionUtil.nextPositionClockwise(duijiaPosition);
			}
		}
		if (pan.getShuangkouPlayerList().size() > 2) {
			for (ShuangkouPlayerValueObject player : pan.getShuangkouPlayerList()) {
				if (player.getId().equals(playerId) || (player.getPosition().equals(duijiaPosition) && shuangming)
						|| (player.getPosition().equals(duijiaPosition) && filterPlayer.getTotalShoupai() == 0)) {// 是自己或者对家,或者自己出完牌可以看到对家牌

					// 什么都不过滤，全要看
				} else {// 是其他玩家
					player.setAllShoupai(null);
					player.setShoupaiDianShuAmountArray(null);
					player.setShoupaiIdListForSortList(null);
					player.setYaPaiSolutionCandidates(null);
					player.setYaPaiSolutionsForTips(null);
				}
			}
		} else {
			for (ShuangkouPlayerValueObject player : pan.getShuangkouPlayerList()) {
				if (player.getId().equals(playerId)) {// 是自己
					// 什么都不过滤，全要看
				} else {// 是其他玩家
					player.setAllShoupai(null);
					player.setLiangPaiList(null);
					player.setShoupaiDianShuAmountArray(null);
					player.setShoupaiIdListForSortList(null);
					player.setYaPaiSolutionCandidates(null);
					player.setYaPaiSolutionsForTips(null);
				}
			}
		}
		return frame.getPanActionFrame();
	}
}
