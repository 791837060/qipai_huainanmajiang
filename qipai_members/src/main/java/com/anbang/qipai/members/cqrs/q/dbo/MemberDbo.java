package com.anbang.qipai.members.cqrs.q.dbo;

public class MemberDbo {
	private String id;// 会员id
	private String nickname;// 会员昵称
	private String gender;// 会员性别:男:male,女:female
	private String headimgurl;// 头像url
	private String phone;// 会员手机
	private long createTime;// 注册时间
	private String realName;// 真实姓名
	private String idCard;// 身份证
	private boolean verifyUser;// 实名认证，true:通过认证,false:未通过认证
	private int gold;// 金币
	private double cost;// 累计消费
	private String reqIP;// 注册ip
    private boolean verifyWeChat;
    private boolean dalianmeng;
    private boolean qinyouquan;

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getReqIP() {
        return reqIP;
    }

    public void setReqIP(String reqIP) {
        this.reqIP = reqIP;
    }

    public boolean isVerifyWeChat() {
        return verifyWeChat;
    }

    public void setVerifyWeChat(boolean verifyWeChat) {
        this.verifyWeChat = verifyWeChat;
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
