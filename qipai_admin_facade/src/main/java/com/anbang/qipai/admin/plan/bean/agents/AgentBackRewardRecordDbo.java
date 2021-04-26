package com.anbang.qipai.admin.plan.bean.agents;

import com.dml.accounting.AccountingSummary;

public class AgentBackRewardRecordDbo {
	private String id;

	private String accountId;

	private long accountingNo;

	private double accountingAmount;

	private double balanceAfter;

	private AccountingSummary summary;

	private long accountingTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
