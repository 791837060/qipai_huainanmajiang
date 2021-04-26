package com.anbang.qipai.admin.msg.msjobj;

import com.dml.accounting.AccountingRecord;

/**
 * 话费红包点等类型积分兑换
 */
public class ScoreExchangeMo {
    private String id;
    private String memberId;
    private String phone;
    private long time;
    /**
     * 兑换的积分数量
     */
    private int score;
    /**
     * 兑换的话费或红包数量
     */
    private int currency;
    private ExchangeTypeEnum exchangeTypeEnum;

    private int rest;

    AccountingRecord accountingRecord;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public ExchangeTypeEnum getExchangeTypeEnum() {
        return exchangeTypeEnum;
    }

    public void setExchangeTypeEnum(ExchangeTypeEnum exchangeTypeEnum) {
        this.exchangeTypeEnum = exchangeTypeEnum;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public AccountingRecord getAccountingRecord() {
        return accountingRecord;
    }

    public void setAccountingRecord(AccountingRecord accountingRecord) {
        this.accountingRecord = accountingRecord;
    }
}
