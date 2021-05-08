package com.anbang.qipai.shouxianmajiang.cqrs.c.domain.piao;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerVotedWhenXiapiao implements GamePlayerState {

	public static final String name = "PlayerVotedWhenXiapiao";

	@Override
	public String name() {
		return name;
	}

}
