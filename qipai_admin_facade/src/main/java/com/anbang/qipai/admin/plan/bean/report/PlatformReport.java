package com.anbang.qipai.admin.plan.bean.report;

public class PlatformReport {
	private String id;
	private long date;// 日期
	private int newMember;// 新增注册量
	private int currentMember;// 当日会员人数
	private double cost;// 消费金额
	private int gameNum;// 游戏总局数
	private int loginMember;// 独立玩家
	private int remainSecond;// 次日留存
	private int remainThird;// 三日留存
	private int remainSeventh;// 七日留存
	private int remainMonth;// 30日以外留存

	public PlatformReport() {

	}

	public PlatformReport(long date, int newMember, int currentMember, double cost, int gameNum, int loginMember,
			int remainSecond, int remainThird, int remainSeventh, int remainMonth) {
		this.date = date;
		this.newMember = newMember;
		this.currentMember = currentMember;
		this.cost = cost;
		this.gameNum = gameNum;
		this.loginMember = loginMember;
		this.remainSecond = remainSecond;
		this.remainThird = remainThird;
		this.remainSeventh = remainSeventh;
		this.remainMonth = remainMonth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getNewMember() {
		return newMember;
	}

	public void setNewMember(int newMember) {
		this.newMember = newMember;
	}

	public int getCurrentMember() {
		return currentMember;
	}

	public void setCurrentMember(int currentMember) {
		this.currentMember = currentMember;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getGameNum() {
		return gameNum;
	}

	public void setGameNum(int gameNum) {
		this.gameNum = gameNum;
	}

	public int getLoginMember() {
		return loginMember;
	}

	public void setLoginMember(int loginMember) {
		this.loginMember = loginMember;
	}

	public int getRemainSecond() {
		return remainSecond;
	}

	public void setRemainSecond(int remainSecond) {
		this.remainSecond = remainSecond;
	}

	public int getRemainThird() {
		return remainThird;
	}

	public void setRemainThird(int remainThird) {
		this.remainThird = remainThird;
	}

	public int getRemainSeventh() {
		return remainSeventh;
	}

	public void setRemainSeventh(int remainSeventh) {
		this.remainSeventh = remainSeventh;
	}

	public int getRemainMonth() {
		return remainMonth;
	}

	public void setRemainMonth(int remainMonth) {
		this.remainMonth = remainMonth;
	}

}
