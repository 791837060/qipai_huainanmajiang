package com.anbang.qipai.doudizhu.cqrs.c.domain.state;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerQiangdizhu implements GamePlayerState {

	public static final String name = "PlayerQiangdizhu";

	@Override
	public String name() {
		return name;
	}

}
