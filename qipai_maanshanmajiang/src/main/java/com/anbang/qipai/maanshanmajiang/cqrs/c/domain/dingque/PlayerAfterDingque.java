package com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerAfterDingque implements GamePlayerState {

	public static final String name = "PlayerAfterDingque";

	@Override
	public String name() {
		return name;
	}

}
