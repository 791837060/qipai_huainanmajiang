package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanPlayerResult;
import com.dml.doudizhu.player.DoudizhuPlayerValueObject;

public class DoudizhuPanPlayerResultDbo {

	private String playerId;
	private DoudizhuPanPlayerResult playerResult;
	private DoudizhuPlayerValueObject player;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public DoudizhuPanPlayerResult getPlayerResult() {
		return playerResult;
	}

	public void setPlayerResult(DoudizhuPanPlayerResult playerResult) {
		this.playerResult = playerResult;
	}

	public DoudizhuPlayerValueObject getPlayer() {
		return player;
	}

	public void setPlayer(DoudizhuPlayerValueObject player) {
		this.player = player;
	}

}
