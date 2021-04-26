package com.anbang.qipai.biji.cqrs.c.domain;

import java.util.List;

import com.anbang.qipai.biji.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import com.dml.shisanshui.pan.PanActionFrame;
import com.dml.shisanshui.player.ShisanshuiPlayerValueObject;

public class PlayerActionFrameFilter {

	public PanActionFrame filter(GameLatestPanActionFrameDbo frame, String playerId) {
		PanActionFrame panActionFrame = frame.getPanActionFrame();
		panActionFrame.setAction(null);
		List<ShisanshuiPlayerValueObject> playerList = panActionFrame.getPanAfterAction().getPlayerList();
		for (ShisanshuiPlayerValueObject player : playerList) {
			if (!playerId.equals(player.getId())) {
				player.setAllShoupai(null);
				player.setShoupaiIdListForSortList(null);
				player.setDuiziCandidates(null);
				player.setShunziCandidates(null);
				player.setTonghuaCandidates(null);
				player.setTonghuashunCandidates(null);
				player.setSantiaoCandidates(null);
				player.setChupaiSolutionForTips(null);
			}
		}
		return panActionFrame;
	}
}
