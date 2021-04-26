package com.anbang.qipai.doudizhu.cqrs.c.domain.state;

import com.dml.mpgame.game.GameState;

public class VotingWhenQiangdizhu implements GameState {

	public static final String name = "VotingWhenQiangdizhu";

	@Override
	public String name() {
		return name;
	}

}
