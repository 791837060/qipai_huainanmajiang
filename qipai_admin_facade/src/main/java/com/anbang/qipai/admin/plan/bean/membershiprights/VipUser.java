package com.anbang.qipai.admin.plan.bean.membershiprights;

public class VipUser {
	private String id;
 	
 	private int signGoldNumber;//用户签到得金币数量
 	
 	private int inviteIntegralNumber;//邀请得积分数量
 	
 	private float vipGrowIntegralSpeed;//会员积分增长速度
 	
 	private float vipGrowGradeSpeed;//会员等级增长速度
 	
 	private int vipMemberRoomsCount;//vip保存房间数量
 	
 	private int vipMemberRoomsAliveHours;//会员房间存活小时数

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

	public int getInviteIntegralNumber() {
		return inviteIntegralNumber;
	}

	public void setInviteIntegralNumber(int inviteIntegralNumber) {
		this.inviteIntegralNumber = inviteIntegralNumber;
	}

	public float getVipGrowIntegralSpeed() {
		return vipGrowIntegralSpeed;
	}

	public void setVipGrowIntegralSpeed(float vipGrowIntegralSpeed) {
		this.vipGrowIntegralSpeed = vipGrowIntegralSpeed;
	}

	public float getVipGrowGradeSpeed() {
		return vipGrowGradeSpeed;
	}

	public void setVipGrowGradeSpeed(float vipGrowGradeSpeed) {
		this.vipGrowGradeSpeed = vipGrowGradeSpeed;
	}

	public int getVipMemberRoomsCount() {
		return vipMemberRoomsCount;
	}

	public void setVipMemberRoomsCount(int vipMemberRoomsCount) {
		this.vipMemberRoomsCount = vipMemberRoomsCount;
	}

	public int getVipMemberRoomsAliveHours() {
		return vipMemberRoomsAliveHours;
	}

	public void setVipMemberRoomsAliveHours(int vipMemberRoomsAliveHours) {
		this.vipMemberRoomsAliveHours = vipMemberRoomsAliveHours;
	}
 	
 	
}
