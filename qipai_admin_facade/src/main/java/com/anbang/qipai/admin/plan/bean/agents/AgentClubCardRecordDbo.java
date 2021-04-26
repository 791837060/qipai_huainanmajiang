package com.anbang.qipai.admin.plan.bean.agents;

import com.dml.accounting.AccountingSummary;

public class AgentClubCardRecordDbo {
	private String id;

	private String accountId;

	private String agentId;

	private String agent;

	private String product;

	private int scoreAccountingAmount;// 消耗积分

	private int balanceAfter;// 剩余积分

	private String receiverId;// 使用对象

	private String receiver;

	private long accountingNo;

	private int accountingAmount;

	private int balanceAfterZhou;// 剩余周卡

	private int balanceAfterYue;// 剩余月卡

	private int balanceAfterJi;// 剩余季卡

	private int balanceAfterRi;// 剩余季卡

	private AccountingSummary summary;

	private long accountingTime;

	private double cost;// 累计消费

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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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

	public int getBalanceAfterZhou() {
		return balanceAfterZhou;
	}

	public void setBalanceAfterZhou(int balanceAfterZhou) {
		this.balanceAfterZhou = balanceAfterZhou;
	}

	public int getBalanceAfterYue() {
		return balanceAfterYue;
	}

	public void setBalanceAfterYue(int balanceAfterYue) {
		this.balanceAfterYue = balanceAfterYue;
	}

	public int getBalanceAfterJi() {
		return balanceAfterJi;
	}

	public void setBalanceAfterJi(int balanceAfterJi) {
		this.balanceAfterJi = balanceAfterJi;
	}

	public int getBalanceAfterRi() {
		return balanceAfterRi;
	}

	public void setBalanceAfterRi(int balanceAfterRi) {
		this.balanceAfterRi = balanceAfterRi;
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

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getScoreAccountingAmount() {
		return scoreAccountingAmount;
	}

	public void setScoreAccountingAmount(int scoreAccountingAmount) {
		this.scoreAccountingAmount = scoreAccountingAmount;
	}

	public int getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(int balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

}
