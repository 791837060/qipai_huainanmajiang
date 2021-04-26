package com.anbang.qipai.dalianmeng.web.vo;


public class ScoreAccountingRecordVO {
    private String memberId;//玩家
    private String referer;//来源Id
    private String refererNickname;
    private String refererHeadimgurl;
    private double accountAmount;//交易额
    private double balance;//余额
    private long accountingTime;//交易时间

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getRefererNickname() {
        return refererNickname;
    }

    public void setRefererNickname(String refererNickname) {
        this.refererNickname = refererNickname;
    }

    public String getRefererHeadimgurl() {
        return refererHeadimgurl;
    }

    public void setRefererHeadimgurl(String refererHeadimgurl) {
        this.refererHeadimgurl = refererHeadimgurl;
    }

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getAccountingTime() {
        return accountingTime;
    }

    public void setAccountingTime(long accountingTime) {
        this.accountingTime = accountingTime;
    }
}
