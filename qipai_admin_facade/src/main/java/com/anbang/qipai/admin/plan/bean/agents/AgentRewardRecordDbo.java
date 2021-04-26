package com.anbang.qipai.admin.plan.bean.agents;

import com.dml.accounting.AccountingSummary;

public class AgentRewardRecordDbo {
	private String id;
	private String agentId;

	private String agentName;

	private String bossId;

	private String bossName;

	private String memberId;

	private String memberName;

	private String memberHeadimgurl;

	private String pruduct;// 商品名称

	private double totalamount;// 总消费

	private double seniorReward;// 推广员收益

	private String accountId;

	private long accountingNo;

	private double accountingAmount;// 推广员收益

	private double balanceAfter;

	private AccountingSummary summary;

	private long accountingTime;

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

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberHeadimgurl() {
		return memberHeadimgurl;
	}

	public void setMemberHeadimgurl(String memberHeadimgurl) {
		this.memberHeadimgurl = memberHeadimgurl;
	}

	public String getPruduct() {
		return pruduct;
	}

	public void setPruduct(String pruduct) {
		this.pruduct = pruduct;
	}

	public double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}

	public double getSeniorReward() {
		return seniorReward;
	}

	public void setSeniorReward(double seniorReward) {
		this.seniorReward = seniorReward;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public long getAccountingNo() {
		return accountingNo;
	}

	public void setAccountingNo(long accountingNo) {
		this.accountingNo = accountingNo;
	}

	public double getAccountingAmount() {
		return accountingAmount;
	}

	public void setAccountingAmount(double accountingAmount) {
		this.accountingAmount = accountingAmount;
	}

	public double getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(double balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public AccountingSummary getSummary() {
		return summary;
	}

	public void setSummary(AccountingSummary summary) {
		this.summary = summary;
	}

	public long getAccountingTime() {
		return accountingTime;
	}

	public void setAccountingTime(long accountingTime) {
		this.accountingTime = accountingTime;
	}

}
