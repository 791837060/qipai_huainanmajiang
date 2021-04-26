package com.dml.doudizhu.pan;

import java.util.List;

import com.dml.doudizhu.player.DoudizhuPlayerValueObject;
import com.dml.puke.wanfa.position.Position;

public abstract class PanResult {

	private long panFinishTime;
	private PanValueObject pan;

	public Position playerPosition(String playerId) {
		return pan.playerPosition(playerId);
	}

	public List<String> allPlayerIds() {
		return pan.allPlayerIds();
	}

	public DoudizhuPlayerValueObject findPlayer(String playerId) {
		return pan.findPlayer(playerId);
	}

	public long getPanFinishTime() {
		return panFinishTime;
	}

	public void setPanFinishTime(long panFinishTime) {
		this.panFinishTime = panFinishTime;
	}

	public PanValueObject getPan() {
		return pan;
	}

	public void setPan(PanValueObject pan) {
		this.pan = pan;
	}

}
