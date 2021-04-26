package com.anbang.qipai.dalianmeng.cqrs.q.dbo;

/**
 * 能量账户
 */
public class PowerAccountDbo {
    private String id;
    private String memberId;
    private String lianmengId;
    private double balance;// 余额

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

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
