package com.anbang.qipai.doudizhu.cqrs.c.domain.state;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerVotingWhenAfterQiangdizhu implements GamePlayerState {

	public static final String name = "PlayerVotingWhenAfterQiangdizhu";

	@Override
	public String name() {
		return name;
	}

}
