package com.anbang.qipai.admin.plan.bean.mail;

public class MailList {
	
	private String id;//邮件状态id
	
	private String memberId;//会员编号
	
	private String receive;//是否领取
	
	private long rewardTime;//领取时间
	
	private String vipCardName;//会员卡名称
	
	private SystemMail systemMail;//邮件内容
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVipCardName() {
		return vipCardName;
	}

	public void setVipCardName(String vipCardName) {
		this.vipCardName = vipCardName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public long getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(long rewardTime) {
		this.rewardTime = rewardTime;
	}

	public SystemMail getSystemMail() {
		return systemMail;
	}

	public void setSystemMail(SystemMail systemMail) {
		this.systemMail = systemMail;
	}
	
	

}
