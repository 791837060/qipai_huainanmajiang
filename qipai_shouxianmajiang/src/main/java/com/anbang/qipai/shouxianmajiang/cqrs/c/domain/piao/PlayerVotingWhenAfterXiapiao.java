package com.anbang.qipai.shouxianmajiang.cqrs.c.domain.piao;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerVotingWhenAfterXiapiao implements GamePlayerState {

	public static final String name = "PlayerVotingWhenAfterXiapiao";

	@Override
	public String name() {
		return name;
	}

}
