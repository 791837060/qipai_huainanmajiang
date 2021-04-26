package com.anbang.qipai.doudizhu.cqrs.c.domain.listener;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.player.DoudizhuPlayer;
import com.dml.doudizhu.player.action.da.DaAction;
import com.dml.doudizhu.player.action.da.DaActionStatisticsListener;

/**
 * 检测春天和反春天可能
 * 
 * @author lsc
 *
 */
public class ChuntainAndFanchuntianOpportunityDetector implements DaActionStatisticsListener {

	/**
	 * 地主打牌次数
	 */
	private int dizhuDaCount;

	/**
	 * 农民打牌次数
	 */
	private int nongminDaCount;

	public boolean isChuntian() {
		return nongminDaCount == 0;
	}

	public boolean isFanChuntian() {
		return dizhuDaCount == 1;
	}

	@Override
	public void updateForNextPan() {
		dizhuDaCount = 0;
		nongminDaCount = 0;
	}

	@Override
	public void update(DaAction daAction, Ju ju) {
		Pan currentPan = ju.getCurrentPan();
		DoudizhuPlayer daPlayer = currentPan.findPlayerById(daAction.getActionPlayerId());
		if (currentPan.getDizhuPlayerId().equals(daPlayer.getId())) {
			dizhuDaCount++;
		} else {
			nongminDaCount++;
		}
	}

	public int getDizhuDaCount() {
		return dizhuDaCount;
	}

	public void setDizhuDaCount(int dizhuDaCount) {
		this.dizhuDaCount = dizhuDaCount;
	}

	public int getNongminDaCount() {
		return nongminDaCount;
	}

	public void setNongminDaCount(int nongminDaCount) {
		this.nongminDaCount = nongminDaCount;
	}

}
