package com.anbang.qipai.doudizhu.cqrs.c.domain.state;

import com.dml.mpgame.game.GameState;

public class Qiangdizhu implements GameState {

	public static final String name = "Qiangdizhu";

	@Override
	public String name() {
		return name;
	}

}
