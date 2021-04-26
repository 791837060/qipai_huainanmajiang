package com.anbang.qipai.paodekuai.cqrs.c.domain.result;

public class PaodekuaiPanPlayerResult {
	private String playerId;
	private int zhadanCount; 	//炸弹数
	private boolean baodan; 	//报单
	private boolean guanmen; 	//关门
	private boolean xiaoguan;	//小关
	private boolean fanguan;	//反关
	private boolean zhuaniao; 	//抓鸟
	private double score;		//一盘结算分
	private double totalScore;	//总分

	private boolean win;
	private int guanmenCount;  // 赢家关门几人
	private int yupaiCount;  // 剩余手牌数

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public int getZhadanCount() {
		return zhadanCount;
	}

	public void setZhadanCount(int zhadanCount) {
		this.zhadanCount = zhadanCount;
	}

	public boolean isBaodan() {
		return baodan;
	}

	public void setBaodan(boolean baodan) {
		this.baodan = baodan;
	}

	public boolean isGuanmen() {
		return guanmen;
	}

	public void setGuanmen(boolean guanmen) {
		this.guanmen = guanmen;
	}

	public boolean isZhuaniao() {
		return zhuaniao;
	}

	public void setZhuaniao(boolean zhuaniao) {
		this.zhuaniao = zhuaniao;
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

    public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getGuanmenCount() {
		return guanmenCount;
	}

	public void setGuanmenCount(int guanmenCount) {
		this.guanmenCount = guanmenCount;
	}

	public int getYupaiCount() {
		return yupaiCount;
	}

	public void setYupaiCount(int yupaiCount) {
		this.yupaiCount = yupaiCount;
	}

	public boolean isFanguan() {
		return fanguan;
	}

	public void setFanguan(boolean fanguan) {
		this.fanguan = fanguan;
	}

	public boolean isXiaoguan() {
		return xiaoguan;
	}

	public void setXiaoguan(boolean xiaoguan) {
		this.xiaoguan = xiaoguan;
	}
}
