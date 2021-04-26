package com.anbang.qipai.admin.plan.bean.agents;

public class AgentInvitationRecord {
	private String id;
	private String agentId;
	private String agent;
	private String memberId;// 玩家id
	private String nickname;// 玩家昵称
	private String invitationCode;// 邀请码
	private int rewardScore;// 奖励积分
	private int inviteNum;// 邀请总人数
	private int score;// 累计得分
	private long createTime;// 绑定时间
	private boolean ban;// 是否禁用
	private Boolean haveLogin;  //是否已登录

	private String loginIp;//玩家登录ip
	private String ipAddress;//ip地址
	private String agentType;//代理类型
	private double agentEarn;//特定代理的本次收益

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
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

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public int getRewardScore() {
		return rewardScore;
	}

	public void setRewardScore(int rewardScore) {
		this.rewardScore = rewardScore;
	}

	public int getInviteNum() {
		return inviteNum;
	}

	public void setInviteNum(int inviteNum) {
		this.inviteNum = inviteNum;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public boolean isBan() {
		return ban;
	}

	public void setBan(boolean ban) {
		this.ban = ban;
	}

	public Boolean getHaveLogin() {
		return haveLogin;
	}

	public void setHaveLogin(Boolean haveLogin) {
		this.haveLogin = haveLogin;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public double getAgentEarn() {
		return agentEarn;
	}

	public void setAgentEarn(double agentEarn) {
		this.agentEarn = agentEarn;
	}
}
