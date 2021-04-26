package com.anbang.qipai.admin.plan.bean.agents;

import com.dml.accounting.AccountingSummary;

public class AgentWithdrawRecordDbo {
	private String id;

	private String agentId;

	private String agentName;

	private String agentHeadimgurl;

	private String agentType;

	private String accountId;

	private long accountingNo;

	private double accountingAmount;// 提现金额

	private double balanceAfter;

	private AccountingSummary summary;

	private long accountingTime;

	private String state;// 状态

	private String desc;// 驳回理由

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

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

	public String getAgentHeadimgurl() {
		return agentHeadimgurl;
	}

	public void setAgentHeadimgurl(String agentHeadimgurl) {
		this.agentHeadimgurl = agentHeadimgurl;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
