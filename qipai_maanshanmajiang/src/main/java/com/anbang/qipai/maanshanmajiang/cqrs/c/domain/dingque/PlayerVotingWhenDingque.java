package com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerVotingWhenDingque implements GamePlayerState {

	public static final String name = "PlayerVotingWhenDingque";

	@Override
	public String name() {
		return name;
	}

}
