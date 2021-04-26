package com.anbang.qipai.guandan.cqrs.c.domain.result;

import java.math.BigDecimal;

public class GuandanJuPlayerResult {
	private String playerId;
	private int shuangkouCount;
	private int dankouCount;
	private int pingkouCount;
	private int maxXianshu;
	private double totalScore;

	public void increaseShuangkouCount() {
		shuangkouCount++;
	}

	public void increaseDankouCount() {
		dankouCount++;
	}

	public void increasePingkouCount() {
		pingkouCount++;
	}

	public void increaseTotalScore(double score) {
        totalScore = new BigDecimal(Double.toString(score)).add(new BigDecimal(Double.toString(totalScore))).doubleValue();
	}

	public void tryAndUpdateMaxXianshu(int xianshu) {
		if (xianshu > maxXianshu) {
			maxXianshu = xianshu;
		}
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public int getShuangkouCount() {
		return shuangkouCount;
	}

	public void setShuangkouCount(int shuangkouCount) {
		this.shuangkouCount = shuangkouCount;
	}

	public int getDankouCount() {
		return dankouCount;
	}

	public void setDankouCount(int dankouCount) {
		this.dankouCount = dankouCount;
	}

	public int getPingkouCount() {
		return pingkouCount;
	}

	public void setPingkouCount(int pingkouCount) {
		this.pingkouCount = pingkouCount;
	}

	public int getMaxXianshu() {
		return maxXianshu;
	}

	public void setMaxXianshu(int maxXianshu) {
		this.maxXianshu = maxXianshu;
	}

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}
