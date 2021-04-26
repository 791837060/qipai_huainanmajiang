package com.anbang.qipai.doudizhu.cqrs.c.domain.result;

import com.anbang.qipai.doudizhu.cqrs.c.domain.DoudizhuBeishu;

public class DoudizhuPanPlayerResult {
	private String playerId;
	private boolean ying;
	private boolean dizhu;
	private double difen;
	private DoudizhuBeishu beishu;
	private int zhadanCount;
	private double score;// 一盘结算分
	private double totalScore;// 总分

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public boolean isYing() {
		return ying;
	}

	public void setYing(boolean ying) {
		this.ying = ying;
	}

	public boolean isDizhu() {
		return dizhu;
	}

	public void setDizhu(boolean dizhu) {
		this.dizhu = dizhu;
	}

    public double getDifen() {
        return difen;
    }

    public void setDifen(double difen) {
        this.difen = difen;
    }

    public DoudizhuBeishu getBeishu() {
		return beishu;
	}

	public void setBeishu(DoudizhuBeishu beishu) {
		this.beishu = beishu;
	}

	public int getZhadanCount() {
		return zhadanCount;
	}

	public void setZhadanCount(int zhadanCount) {
		this.zhadanCount = zhadanCount;
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
