package com.anbang.qipai.admin.plan.bean.chaguan;

import com.dml.accounting.AccountingSummary;

/**
 * 玩家茶馆玉石流水
 * 
 * @author lsc
 *
 */
public class MemberChaguanYushiRecordDbo {

	private String id;

	private String accountId;// 账户id

	private String memberId;// 玩家id

	private String agentId;// 馆主id

	private long accountingNo;// 流水号

	private int accountingAmount;// 交易额

	private int balanceAfter;// 余额

	private AccountingSummary summary;// 记账摘要

	private long accountingTime;// 交易时间

	private RewardType type;

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public long getAccountingNo() {
		return accountingNo;
	}

	public void setAccountingNo(long accountingNo) {
		this.accountingNo = accountingNo;
	}

	public int getAccountingAmount() {
		return accountingAmount;
	}

	public void setAccountingAmount(int accountingAmount) {
		this.accountingAmount = accountingAmount;
	}

	public int getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(int balanceAfter) {
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

	public RewardType getType() {
		return type;
	}

	public void setType(RewardType type) {
		this.type = type;
	}
}
