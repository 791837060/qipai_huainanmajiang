package com.anbang.qipai.admin.plan.bean.members;

public class MemberDbo {
	private String id;// 会员id
	private String nickname;// 会员昵称
	private String gender;// 会员性别:男:male,女:female
	private boolean vip;// 是否VIP
	private int vipLevel;// VIP等级
	private double vipScore;// VIP积分
	private String headimgurl;// 头像url
	private String phone;// 会员手机
	private long createTime;// 注册时间
	private long vipEndTime;// VIP时间
	private String realName;// 真实姓名
	private String idCard;// 身份证
	private boolean verifyUser;// 实名认证，true:通过认证,false:未通过认证
	private boolean bindAgent;// 绑定推广员，true:绑定,false:未绑定
	private String agentId;// 绑定推广员id
	private String onlineState;
	private int gold;// 金币
	private int score;// 积分
	private int xiuxianchangGold;// 休闲场金币
	private double cost;// 累计消费
	private String reqIP;// 注册ip
	private String reqIpAddress;// 注册IP归属地
	private int goldTotalCost;	//玉石总消费
    private boolean dalianmeng;
    private boolean qinyouquan;

	private boolean robot;// 是否是机器人

	private String loginIp; // 登录ip
	private String ipAddress; // ip地址

	public int getXiuxianchangGold() {
		return xiuxianchangGold;
	}

	public void setXiuxianchangGold(int xiuxianchangGold) {
		this.xiuxianchangGold = xiuxianchangGold;
	}

	public String getReqIP() {
		return reqIP;
	}

	public void setReqIP(String reqIP) {
		this.reqIP = reqIP;
	}

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

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public double getVipScore() {
		return vipScore;
	}

	public void setVipScore(double vipScore) {
		this.vipScore = vipScore;
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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getVipEndTime() {
		return vipEndTime;
	}

	public void setVipEndTime(long vipEndTime) {
		this.vipEndTime = vipEndTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public boolean isVerifyUser() {
		return verifyUser;
	}

	public void setVerifyUser(boolean verifyUser) {
		this.verifyUser = verifyUser;
	}

	public boolean isBindAgent() {
		return bindAgent;
	}

	public void setBindAgent(boolean bindAgent) {
		this.bindAgent = bindAgent;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(String onlineState) {
		this.onlineState = onlineState;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public boolean isRobot() {
		return robot;
	}

	public void setRobot(boolean robot) {
		this.robot = robot;
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

	public String getReqIpAddress() {
		return reqIpAddress;
	}

	public void setReqIpAddress(String reqIpAddress) {
		this.reqIpAddress = reqIpAddress;
	}

	public int getGoldTotalCost() {
		return goldTotalCost;
	}

	public void setGoldTotalCost(int goldTotalCost) {
		this.goldTotalCost = goldTotalCost;
	}

    public boolean isDalianmeng() {
        return dalianmeng;
    }

    public void setDalianmeng(boolean dalianmeng) {
        this.dalianmeng = dalianmeng;
    }

    public boolean isQinyouquan() {
        return qinyouquan;
    }

    public void setQinyouquan(boolean qinyouquan) {
        this.qinyouquan = qinyouquan;
    }
}
