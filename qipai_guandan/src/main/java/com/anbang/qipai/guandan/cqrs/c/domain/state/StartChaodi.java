package com.anbang.qipai.guandan.cqrs.c.domain.state;

import com.dml.mpgame.game.GameState;

public class StartChaodi implements GameState {
	public static final String name = "StartChaodi";

	@Override
	public String name() {
		return name;
	}

}
