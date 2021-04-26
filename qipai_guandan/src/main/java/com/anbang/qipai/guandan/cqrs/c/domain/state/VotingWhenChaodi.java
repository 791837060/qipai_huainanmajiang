package com.anbang.qipai.guandan.cqrs.c.domain.state;

import com.dml.mpgame.game.GameState;

public class VotingWhenChaodi implements GameState {

	public static final String name = "VotingWhenChaodi";

	@Override
	public String name() {
		return name;
	}

}
