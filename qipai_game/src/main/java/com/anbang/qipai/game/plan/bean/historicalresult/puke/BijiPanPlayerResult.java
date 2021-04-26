package com.anbang.qipai.game.plan.bean.historicalresult.puke;

import com.anbang.qipai.game.plan.bean.historicalresult.GamePanPlayerResult;

import java.util.Map;

public class BijiPanPlayerResult implements GamePanPlayerResult {
	private String playerId;// 玩家id
	private String nickname;// 玩家昵称
	private double score;// 一盘总分



	public BijiPanPlayerResult() {

	}

	public BijiPanPlayerResult(Map panPlayerResult) {
		this.playerId = (String) panPlayerResult.get("playerId");
		this.nickname = (String) panPlayerResult.get("nickname");
		this.score = ((Double) panPlayerResult.get("score")).intValue();
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
