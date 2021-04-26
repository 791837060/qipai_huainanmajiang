package com.anbang.qipai.admin.plan.bean.agents;

public class AgentDbo {
	private String id;// 推广员id
	private String nickname;// 推广员昵称
	private String gender;// 推广员性别:男:male,女:female
	private String headimgurl;// 头像url
	private String phone;// 推广员手机
	private String userName;// 姓名
	private String area;// 所在区域
	private String desc;// 备注
	private String bossId;// 上级id
	private String bossName;// 上级昵称
	private long createTime;
	private boolean agentAuth;// 是否通过推广员申请
	private String state;// 正常,封停
	private String invitationCode;// 邀请码
	private double cost;// 充值金额
	private AgentType agentType;// 推广员类型
	private int inviteMemberNum;// 邀请玩家数量
	private int juniorNum;// 下级数量

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getBossId() {
		return bossId;
	}

	public void setBossId(String bossId) {
		this.bossId = bossId;
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public boolean isAgentAuth() {
		return agentAuth;
	}

	public void setAgentAuth(boolean agentAuth) {
		this.agentAuth = agentAuth;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public AgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}

	public int getInviteMemberNum() {
		return inviteMemberNum;
	}

	public void setInviteMemberNum(int inviteMemberNum) {
		this.inviteMemberNum = inviteMemberNum;
	}

	public int getJuniorNum() {
		return juniorNum;
	}

	public void setJuniorNum(int juniorNum) {
		this.juniorNum = juniorNum;
	}

}
