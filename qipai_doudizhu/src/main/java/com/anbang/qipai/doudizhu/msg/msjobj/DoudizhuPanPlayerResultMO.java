package com.anbang.qipai.doudizhu.msg.msjobj;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.DoudizhuPanPlayerResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGamePlayerDbo;

public class DoudizhuPanPlayerResultMO {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private boolean ying;
	private double difen;
	private int beishu;
	private double score;// 一盘结算分
	private double totalScore;// 总分

	public DoudizhuPanPlayerResultMO() {

	}

	public DoudizhuPanPlayerResultMO(PukeGamePlayerDbo playerDbo, DoudizhuPanPlayerResultDbo panPlayerResult) {
		playerId = playerDbo.getPlayerId();
		nickname = playerDbo.getNickname();
		headimgurl = playerDbo.getHeadimgurl();
		ying = panPlayerResult.getPlayerResult().isYing();
		difen = panPlayerResult.getPlayerResult().getDifen();
		beishu = panPlayerResult.getPlayerResult().getBeishu().getValue();
		score = panPlayerResult.getPlayerResult().getScore();
		totalScore = panPlayerResult.getPlayerResult().getTotalScore();
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

	public boolean isYing() {
		return ying;
	}

	public void setYing(boolean ying) {
		this.ying = ying;
	}

    public double getDifen() {
        return difen;
    }

    public void setDifen(double difen) {
        this.difen = difen;
    }

    public int getBeishu() {
        return beishu;
    }

    public void setBeishu(int beishu) {
        this.beishu = beishu;
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
