package com.anbang.qipai.guandan.cqrs.c.domain.state;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerVotedWhenChaodi implements GamePlayerState {

	public static final String name = "PlayerVotedWhenChaodi";

	@Override
	public String name() {
		return name;
	}

}
