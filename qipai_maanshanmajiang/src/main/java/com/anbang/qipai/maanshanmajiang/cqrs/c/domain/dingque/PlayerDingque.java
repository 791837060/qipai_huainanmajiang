package com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerDingque implements GamePlayerState {

	public static final String name = "PlayerDingque";

	@Override
	public String name() {
		return name;
	}

}
