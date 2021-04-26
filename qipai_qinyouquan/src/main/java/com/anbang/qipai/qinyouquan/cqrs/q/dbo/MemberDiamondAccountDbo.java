package com.anbang.qipai.qinyouquan.cqrs.q.dbo;

/**
 * 能量账户
 */
public class MemberDiamondAccountDbo {
    private String id;
    private String memberId;
    private String lianmengId;
    private int balance;// 余额

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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
