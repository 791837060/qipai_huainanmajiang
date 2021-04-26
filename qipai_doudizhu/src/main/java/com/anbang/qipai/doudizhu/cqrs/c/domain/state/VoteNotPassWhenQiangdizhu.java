package com.anbang.qipai.doudizhu.cqrs.c.domain.state;

import com.dml.mpgame.game.GameState;

public class VoteNotPassWhenQiangdizhu implements GameState {

	public static final String name = "VoteNotPassWhenQiangdizhu";

	@Override
	public String name() {
		return name;
	}

}
