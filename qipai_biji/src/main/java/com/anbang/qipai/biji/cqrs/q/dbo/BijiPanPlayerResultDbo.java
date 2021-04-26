package com.anbang.qipai.biji.cqrs.q.dbo;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanPlayerResult;
import com.dml.shisanshui.player.ShisanshuiPlayerValueObject;

public class BijiPanPlayerResultDbo {

	private String playerId;
	private BijiPanPlayerResult playerResult;
	private ShisanshuiPlayerValueObject player;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public BijiPanPlayerResult getPlayerResult() {
		return playerResult;
	}

	public void setPlayerResult(BijiPanPlayerResult playerResult) {
		this.playerResult = playerResult;
	}

	public ShisanshuiPlayerValueObject getPlayer() {
		return player;
	}

	public void setPlayer(ShisanshuiPlayerValueObject player) {
		this.player = player;
	}

}
