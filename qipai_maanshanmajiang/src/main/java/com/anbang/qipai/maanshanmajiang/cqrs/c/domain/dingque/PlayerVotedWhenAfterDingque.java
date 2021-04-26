package com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerVotedWhenAfterDingque implements GamePlayerState {

	public static final String name = "PlayerVotedWhenAfterDingque";

	@Override
	public String name() {
		return name;
	}

}
