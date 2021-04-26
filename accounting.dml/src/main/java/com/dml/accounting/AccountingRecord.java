package com.dml.accounting;

/**
 * 记账记录
 * 
 * @author neo
 *
 */
public class AccountingRecord {

	/**
	 * 记账帐户id
	 */
	private String accountId;

	/**
	 * 帐户记账流水号
	 */
	private long accountingNo;

	/**
	 * 记账金额，正数为收入，负数为支出
	 */
	private double accountingAmount;

	/**
	 * 后账户余额
	 */
	private double balanceAfter;

	/**
	 * 记账摘要
	 */
	private AccountingSummary summary;

	/**
	 * 记账时间
	 */
	private long accountingTime;

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
