package com.dml.doudizhu.player.action.da;

import com.dml.doudizhu.ju.Ju;

public interface DaActionStatisticsListener {

	public void updateForNextPan();

	public void update(DaAction daAction, Ju ju);
}
