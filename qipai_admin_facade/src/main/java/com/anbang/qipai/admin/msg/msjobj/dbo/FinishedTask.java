package com.anbang.qipai.admin.msg.msjobj.dbo;

/**
 * 完成的任务
 **/
public class FinishedTask {

	private String id;

	private String memberId;// 玩家id

	private String nickname;// 昵称

	private RewardType rewardType;// 奖励类型

	private double rewardNum;// 奖励数量

	private String getRewardIP;// 领奖IP

	private long finishTime;// 领取时间

	private Task task;// 任务

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public RewardType getRewardType() {
		return rewardType;
	}

	public void setRewardType(RewardType rewardType) {
		this.rewardType = rewardType;
	}

	public double getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(double rewardNum) {
		this.rewardNum = rewardNum;
	}

	public String getGetRewardIP() {
		return getRewardIP;
	}

	public void setGetRewardIP(String getRewardIP) {
		this.getRewardIP = getRewardIP;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
