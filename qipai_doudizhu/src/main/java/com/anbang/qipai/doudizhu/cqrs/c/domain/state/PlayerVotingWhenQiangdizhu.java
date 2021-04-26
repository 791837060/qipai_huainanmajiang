package com.anbang.qipai.doudizhu.cqrs.c.domain.state;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerVotingWhenQiangdizhu implements GamePlayerState {

	public static final String name = "PlayerVotingWhenQiangdizhu";

	@Override
	public String name() {
		return name;
	}

}
