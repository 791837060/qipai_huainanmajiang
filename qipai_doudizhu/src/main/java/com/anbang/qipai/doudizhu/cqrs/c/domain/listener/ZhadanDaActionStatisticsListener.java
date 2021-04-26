package com.anbang.qipai.doudizhu.cqrs.c.domain.listener;

import java.util.HashMap;
import java.util.Map;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.player.action.da.DaAction;
import com.dml.doudizhu.player.action.da.DaActionStatisticsListener;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;

public class ZhadanDaActionStatisticsListener implements DaActionStatisticsListener {

	private int dachuZhadanCount;
	private Map<String, Integer> playerZhadanCountMap = new HashMap<>();

	@Override
	public void updateForNextPan() {
		dachuZhadanCount = 0;
		playerZhadanCountMap.clear();
	}

	@Override
	public void update(DaAction daAction, Ju ju) {
		DianShuZu dianShuZu = daAction.getDachuPaiZu().getDianShuZu();
		if (dianShuZu instanceof ZhadanDianShuZu) {
			dachuZhadanCount++;
			Integer count = playerZhadanCountMap.get(daAction.getActionPlayerId());
			if (count == null) {
				count = 1;
			} else {
				count += 1;
			}
			playerZhadanCountMap.put(daAction.getActionPlayerId(), count);
		}
	}

	public int getDachuZhadanCount() {
		return dachuZhadanCount;
	}

	public void setDachuZhadanCount(int dachuZhadanCount) {
		this.dachuZhadanCount = dachuZhadanCount;
	}

	public Map<String, Integer> getPlayerZhadanCountMap() {
		return playerZhadanCountMap;
	}

	public void setPlayerZhadanCountMap(Map<String, Integer> playerZhadanCountMap) {
		this.playerZhadanCountMap = playerZhadanCountMap;
	}

}
