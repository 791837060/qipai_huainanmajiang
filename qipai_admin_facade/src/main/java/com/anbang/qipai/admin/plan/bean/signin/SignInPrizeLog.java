package com.anbang.qipai.admin.plan.bean.signin;

public class SignInPrizeLog {//签到奖品纪录
	private String id;//中奖记录
	private String memberId;
	private String nickname;//用户昵称
	private String signInPrizeId;
	private String name;
	private String type;
	private int singleNum;
	private long createTime;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSingleNum() {
		return singleNum;
	}
	public void setSingleNum(int singleNum) {
		this.singleNum = singleNum;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getSignInPrizeId() {
		return signInPrizeId;
	}
	public void setSignInPrizeId(String signInPrizeId) {
		this.signInPrizeId = signInPrizeId;
	}
	
}
