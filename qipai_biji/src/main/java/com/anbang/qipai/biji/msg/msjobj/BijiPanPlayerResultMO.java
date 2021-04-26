package com.anbang.qipai.biji.msg.msjobj;

import com.anbang.qipai.biji.cqrs.q.dbo.BijiPanPlayerResultDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.PukeGamePlayerDbo;

public class BijiPanPlayerResultMO {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private double score;// 一盘结算分
	private double totalScore;// 总分

	public BijiPanPlayerResultMO() {

	}

	public BijiPanPlayerResultMO(PukeGamePlayerDbo playerDbo, BijiPanPlayerResultDbo panPlayerResult) {
		playerId = playerDbo.getPlayerId();
		nickname = playerDbo.getNickname();
		headimgurl = playerDbo.getHeadimgurl();
		score=panPlayerResult.getPlayerResult().getJiesuanScore().getValue();
		totalScore=panPlayerResult.getPlayerResult().getTotalScore();
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

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

}
