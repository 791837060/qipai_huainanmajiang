package com.anbang.qipai.paodekuai.msg.msjobj;

import com.anbang.qipai.paodekuai.cqrs.q.dbo.PukeGamePlayerDbo;
import com.anbang.qipai.paodekuai.cqrs.q.dbo.PaodekuaiPanPlayerResultDbo;

public class PaodekuaiPanPlayerResultMO {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private double score;// 一盘结算分
	private double totalScore;// 总分
	private boolean ying;

	public PaodekuaiPanPlayerResultMO() {

	}

	public PaodekuaiPanPlayerResultMO(PukeGamePlayerDbo playerDbo,
									  PaodekuaiPanPlayerResultDbo panPlayerResult) {
		playerId = playerDbo.getPlayerId();
		nickname = playerDbo.getNickname();
		headimgurl = playerDbo.getHeadimgurl();
		score = panPlayerResult.getPlayerResult().getScore();
		totalScore = panPlayerResult.getPlayerResult().getTotalScore();
		ying = panPlayerResult.getPlayerResult().isWin();
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

    public boolean isYing() {
		return ying;
	}

	public void setYing(boolean ying) {
		this.ying = ying;
	}
}
