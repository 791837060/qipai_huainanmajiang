package com.anbang.qipai.qinyouquan.cqrs.q.dbo;

/**
 * 玩家联盟充值账户
 */
public class LianmengDiamondAccountDbo {
    private String id;
    private String agentId;
    private int balance;// 余额

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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
