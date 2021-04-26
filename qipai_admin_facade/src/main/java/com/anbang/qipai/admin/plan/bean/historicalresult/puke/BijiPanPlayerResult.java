package com.anbang.qipai.admin.plan.bean.historicalresult.puke;

import com.anbang.qipai.admin.plan.bean.historicalresult.GamePanPlayerResult;

import java.util.Map;

public class BijiPanPlayerResult implements GamePanPlayerResult {
	private String playerId;// 玩家id
	private String nickname;// 玩家昵称
	private int score;// 一盘总分

	// TODO: 2019/3/14
	private int zhadanCount; // 炸弹数
	private boolean baodan; // 报单
	private boolean guanmen; // 关门
	private boolean zhuaniao; //抓鸟

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
