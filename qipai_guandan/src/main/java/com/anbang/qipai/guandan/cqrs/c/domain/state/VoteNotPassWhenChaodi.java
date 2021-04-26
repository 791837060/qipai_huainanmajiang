package com.anbang.qipai.guandan.cqrs.c.domain.state;

import com.dml.mpgame.game.GameState;

public class VoteNotPassWhenChaodi implements GameState {

	public static final String name = "VoteNotPassWhenChaodi";

	@Override
	public String name() {
		return name;
	}

}
