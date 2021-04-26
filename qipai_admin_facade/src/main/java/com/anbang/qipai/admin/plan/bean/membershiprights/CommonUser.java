package com.anbang.qipai.admin.plan.bean.membershiprights;

public class CommonUser {
	private String id;

	private int signGoldNumber;// 用户签到得金币数量

	private int goldForNewNember;// 新用户注册赠送的金币数量

	private int inviteIntegralNumber;// 邀请得积分数量

	private float planGrowIntegralSpeed;// 普通用户积分增长速度

	private int planMemberCreateRoomDailyGoldPrice;// 创建房间的金币价格
	private int planMemberaddRoomDailyGoldPrice;// 加入房间的金币价格

	private int planMemberRoomsCount;// 普通保存房间数量

	private int planMemberMaxCreateRoomDaily;// 普通用户创建vip房间的上限

	private int planMemberRoomsAliveHours;// 普通房间存活小时数

	private int goldForAgentInvite;// 填写邀请码赠送玉石数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSignGoldNumber() {
		return signGoldNumber;
	}

	public void setSignGoldNumber(int signGoldNumber) {
		this.signGoldNumber = signGoldNumber;
	}

	public int getGoldForNewNember() {
		return goldForNewNember;
	}

	public void setGoldForNewNember(int goldForNewNember) {
		this.goldForNewNember = goldForNewNember;
	}

	public int getInviteIntegralNumber() {
		return inviteIntegralNumber;
	}

	public void setInviteIntegralNumber(int inviteIntegralNumber) {
		this.inviteIntegralNumber = inviteIntegralNumber;
	}

	public float getPlanGrowIntegralSpeed() {
		return planGrowIntegralSpeed;
	}

	public void setPlanGrowIntegralSpeed(float planGrowIntegralSpeed) {
		this.planGrowIntegralSpeed = planGrowIntegralSpeed;
	}

	public int getPlanMemberCreateRoomDailyGoldPrice() {
		return planMemberCreateRoomDailyGoldPrice;
	}

	public void setPlanMemberCreateRoomDailyGoldPrice(int planMemberCreateRoomDailyGoldPrice) {
		this.planMemberCreateRoomDailyGoldPrice = planMemberCreateRoomDailyGoldPrice;
	}

	public int getPlanMemberaddRoomDailyGoldPrice() {
		return planMemberaddRoomDailyGoldPrice;
	}

	public void setPlanMemberaddRoomDailyGoldPrice(int planMemberaddRoomDailyGoldPrice) {
		this.planMemberaddRoomDailyGoldPrice = planMemberaddRoomDailyGoldPrice;
	}

	public int getPlanMemberRoomsCount() {
		return planMemberRoomsCount;
	}

	public void setPlanMemberRoomsCount(int planMemberRoomsCount) {
		this.planMemberRoomsCount = planMemberRoomsCount;
	}

	public int getPlanMemberMaxCreateRoomDaily() {
		return planMemberMaxCreateRoomDaily;
	}

	public void setPlanMemberMaxCreateRoomDaily(int planMemberMaxCreateRoomDaily) {
		this.planMemberMaxCreateRoomDaily = planMemberMaxCreateRoomDaily;
	}

	public int getPlanMemberRoomsAliveHours() {
		return planMemberRoomsAliveHours;
	}

	public void setPlanMemberRoomsAliveHours(int planMemberRoomsAliveHours) {
		this.planMemberRoomsAliveHours = planMemberRoomsAliveHours;
	}

	public int getGoldForAgentInvite() {
		return goldForAgentInvite;
	}

	public void setGoldForAgentInvite(int goldForAgentInvite) {
		this.goldForAgentInvite = goldForAgentInvite;
	}

}
