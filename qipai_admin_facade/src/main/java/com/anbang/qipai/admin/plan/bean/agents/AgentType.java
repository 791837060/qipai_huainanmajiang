package com.anbang.qipai.admin.plan.bean.agents;

public class AgentType {
	private String id;
	private String type;
	private double memberReward;// 玩家返利
	private double juniorReward;// 下级返利

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getMemberReward() {
		return memberReward;
	}

	public void setMemberReward(double memberReward) {
		this.memberReward = memberReward;
	}

	public double getJuniorReward() {
		return juniorReward;
	}

	public void setJuniorReward(double juniorReward) {
		this.juniorReward = juniorReward;
	}

}
