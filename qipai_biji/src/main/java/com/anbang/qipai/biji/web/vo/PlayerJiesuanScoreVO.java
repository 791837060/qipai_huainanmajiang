package com.anbang.qipai.biji.web.vo;

import com.anbang.qipai.biji.cqrs.c.domain.result.PlayerJiesuanScore;

public class PlayerJiesuanScoreVO {
	private String playerId;
	private int toudao;// 头道基础分
	private int zhongdao;// 中道基础分
	private int weidao;// 尾道基础分
	private int score;// 基础结算分
	private int value;// 总结算分

	public PlayerJiesuanScoreVO() {

	}

	public PlayerJiesuanScoreVO(PlayerJiesuanScore score) {
		this.playerId = score.getPlayerId();
		this.toudao = score.getToudao();
		this.zhongdao = score.getZhongdao();
		this.weidao = score.getWeidao();
		this.score = score.getScore();
		this.value = score.getValue();
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public int getToudao() {
		return toudao;
	}

	public void setToudao(int toudao) {
		this.toudao = toudao;
	}

	public int getZhongdao() {
		return zhongdao;
	}

	public void setZhongdao(int zhongdao) {
		this.zhongdao = zhongdao;
	}

	public int getWeidao() {
		return weidao;
	}

	public void setWeidao(int weidao) {
		this.weidao = weidao;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
