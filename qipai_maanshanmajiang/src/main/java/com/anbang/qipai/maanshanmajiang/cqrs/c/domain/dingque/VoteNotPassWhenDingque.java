package com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque;

import com.dml.mpgame.game.GameState;

public class VoteNotPassWhenDingque implements GameState {

	public static final String name = "VoteNotPassWhenDingque";

	@Override
	public String name() {
		return name;
	}

}
