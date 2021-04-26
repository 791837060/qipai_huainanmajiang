package com.anbang.qipai.paodekuai.cqrs.c.domain.result;

public class PaodekuaiJuPlayerResult {
	private String playerId;
	private double totalScore;

	private int danguanCount;
	private int shuangguanCount;
	private int boomCount;
	private double maxScore;


	public void increaseDanguanCount(){
		danguanCount ++;
	}

	public void increaseShuangguanCount() {
		shuangguanCount ++;
	}

	public void increaseBoomCount(int boomNum) {
		boomCount = boomCount + boomNum;
	}

	public void increaseTotalScore(double score) {
		totalScore = totalScore + score;
	}

	/**
	 *  ------set/get
	 */

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public int getDanguanCount() {
		return danguanCount;
	}

	public void setDanguanCount(int danguanCount) {
		this.danguanCount = danguanCount;
	}

	public int getShuangguanCount() {
		return shuangguanCount;
	}

	public void setShuangguanCount(int shuangguanCount) {
		this.shuangguanCount = shuangguanCount;
	}

	public int getBoomCount() {
		return boomCount;
	}

	public void setBoomCount(int boomCount) {
		this.boomCount = boomCount;
	}

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }
}
