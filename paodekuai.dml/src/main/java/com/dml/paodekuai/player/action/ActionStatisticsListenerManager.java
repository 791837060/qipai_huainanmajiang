package com.dml.paodekuai.player.action;

import java.util.ArrayList;
import java.util.List;

import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.player.action.da.DaAction;
import com.dml.paodekuai.player.action.da.DaActionStatisticsListener;
import com.dml.paodekuai.player.action.guo.GuoAction;
import com.dml.paodekuai.player.action.guo.GuoActionStatisticsListener;

public class ActionStatisticsListenerManager {

	private List<DaActionStatisticsListener> daActionStatisticsListeners = new ArrayList<>();

	public void updateListenersForNextPan() {
		daActionStatisticsListeners.forEach((listener) -> listener.updateForNextPan());
	}

	public void updateDaActionListener(DaAction daAction, Ju ju) {
		for (DaActionStatisticsListener listener : daActionStatisticsListeners) {
			listener.update(daAction, ju);
		}
	}

	public void addDaListener(DaActionStatisticsListener daActionStatisticsListener) {
		daActionStatisticsListeners.add(daActionStatisticsListener);
	}

	public <T extends DaActionStatisticsListener> T findDaListener(Class<T> type) {
		for (DaActionStatisticsListener listener : daActionStatisticsListeners) {
			if (listener.getClass().equals(type)) {
				return (T) listener;
			}
		}
		return null;
	}

	public List<DaActionStatisticsListener> getDaActionStatisticsListeners() {
		return daActionStatisticsListeners;
	}

	public void setDaActionStatisticsListeners(List<DaActionStatisticsListener> daActionStatisticsListeners) {
		this.daActionStatisticsListeners = daActionStatisticsListeners;
	}

}
