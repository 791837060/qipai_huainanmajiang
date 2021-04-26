package com.anbang.qipai.doudizhu.cqrs.c.domain.result;

public class DoudizhuJuPlayerResult {
	private String playerId;
	private int yingCount;
	private int fanchuntianCount;
	private int chuntianCount;
	private int maxBeishu;
	private double totalScore;

	public void increaseYingCount() {
		yingCount++;
	}

	public void increaseFanchuntianCount() {
		fanchuntianCount++;
	}

	public void increaseChuntianCount() {
		chuntianCount++;
	}

	public void increaseTotalScore(double score) {
		totalScore += score;
	}

	public void tryAndUpdateMaxBeishu(int beishu) {
		if (beishu > maxBeishu) {
			maxBeishu = beishu;
		}
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public int getYingCount() {
		return yingCount;
	}

	public void setYingCount(int yingCount) {
		this.yingCount = yingCount;
	}

	public int getFanchuntianCount() {
		return fanchuntianCount;
	}

	public void setFanchuntianCount(int fanchuntianCount) {
		this.fanchuntianCount = fanchuntianCount;
	}

	public int getChuntianCount() {
		return chuntianCount;
	}

	public void setChuntianCount(int chuntianCount) {
		this.chuntianCount = chuntianCount;
	}

	public int getMaxBeishu() {
		return maxBeishu;
	}

	public void setMaxBeishu(int maxBeishu) {
		this.maxBeishu = maxBeishu;
	}

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}
