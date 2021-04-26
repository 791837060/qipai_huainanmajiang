package com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque;

import com.dml.mpgame.game.GameState;

public class DingqueState implements GameState {

	public static final String name = "DingqueState";

	@Override
	public String name() {
		return name;
	}

}
