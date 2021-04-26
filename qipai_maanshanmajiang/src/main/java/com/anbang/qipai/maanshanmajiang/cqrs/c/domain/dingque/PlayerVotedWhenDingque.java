package com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerVotedWhenDingque implements GamePlayerState {

	public static final String name = "PlayerVotedWhenDingque";

	@Override
	public String name() {
		return name;
	}

}
