package com.anbang.qipai.guandan.cqrs.q.dbo;

import com.anbang.qipai.guandan.cqrs.c.domain.result.GuandanPanPlayerResult;
import com.dml.shuangkou.player.ShuangkouPlayerValueObject;

public class GuandanPanPlayerResultDbo {

	private String playerId;
	private GuandanPanPlayerResult playerResult;
	private ShuangkouPlayerValueObject player;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public GuandanPanPlayerResult getPlayerResult() {
		return playerResult;
	}

	public void setPlayerResult(GuandanPanPlayerResult playerResult) {
		this.playerResult = playerResult;
	}

	public ShuangkouPlayerValueObject getPlayer() {
		return player;
	}

	public void setPlayer(ShuangkouPlayerValueObject player) {
		this.player = player;
	}

}
