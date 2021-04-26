package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;

public class PlayerQiangdizhuInfoDbo {
	private String playerId;
	private PlayerQiangdizhuState state;
	private Integer score;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public PlayerQiangdizhuState getState() {
		return state;
	}

	public void setState(PlayerQiangdizhuState state) {
		this.state = state;
	}

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
