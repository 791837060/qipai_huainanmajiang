package com.anbang.qipai.admin.remote.vo;

public class AccountRemoteVO {
	private boolean success = true;

	private String msg;

	private Object data;

	private int score;

	private int clubCardZhou;

	private int clubCardYue;

	private int clubCardJi;

	private int clubCardRi;

	private int coins;

	private double reward;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getClubCardZhou() {
		return clubCardZhou;
	}

	public void setClubCardZhou(int clubCardZhou) {
		this.clubCardZhou = clubCardZhou;
	}

	public int getClubCardYue() {
		return clubCardYue;
	}

	public void setClubCardYue(int clubCardYue) {
		this.clubCardYue = clubCardYue;
	}

	public int getClubCardJi() {
		return clubCardJi;
	}

	public void setClubCardJi(int clubCardJi) {
		this.clubCardJi = clubCardJi;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getClubCardRi() {
		return clubCardRi;
	}

	public void setClubCardRi(int clubCardRi) {
		this.clubCardRi = clubCardRi;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}

}
