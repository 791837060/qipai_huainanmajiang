package com.anbang.qipai.guandan.cqrs.c.domain;

public class GuandanChaixianbufen {
	private int totalScore;	//原来应得分数
	private int score;		//实际得分
	private int value;		//补分

	public void calculate() {
		value = (totalScore - score) * 2;
	}

	public void jiesuan(int detal) {
		value += detal;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
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
