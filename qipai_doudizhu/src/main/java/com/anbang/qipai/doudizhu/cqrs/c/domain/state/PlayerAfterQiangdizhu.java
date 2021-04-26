package com.anbang.qipai.doudizhu.cqrs.c.domain.state;

import com.dml.mpgame.game.player.GamePlayerState;

public class PlayerAfterQiangdizhu implements GamePlayerState {

	public static final String name = "PlayerAfterQiangdizhu";

	@Override
	public String name() {
		return name;
	}

}
