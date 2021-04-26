package com.dml.shuangkou.player.action.da;

import com.dml.shuangkou.ju.Ju;

public interface DaActionStatisticsListener {

	public void updateForNextPan();

	public void update(DaAction daAction, Ju ju);
}
