package com.anbang.qipai.doudizhu.cqrs.c.domain.state;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerVotedWhenQiangdizhu implements GamePlayerState {

	public static final String name = "PlayerVotedWhenQiangdizhu";

	@Override
	public String name() {
		return name;
	}

}
