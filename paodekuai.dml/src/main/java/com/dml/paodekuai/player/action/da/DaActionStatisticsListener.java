package com.dml.paodekuai.player.action.da;

import com.dml.paodekuai.ju.Ju;

public interface DaActionStatisticsListener {

	void updateForNextPan();

	void update(DaAction daAction, Ju ju);
}
