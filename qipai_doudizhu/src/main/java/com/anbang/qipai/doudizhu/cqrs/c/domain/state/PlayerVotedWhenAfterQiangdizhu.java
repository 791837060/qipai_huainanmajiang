package com.anbang.qipai.doudizhu.cqrs.c.domain.state;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerVotedWhenAfterQiangdizhu implements GamePlayerState {

	public static final String name = "PlayerVotedWhenAfterQiangdizhu";

	@Override
	public String name() {
		return name;
	}

}
