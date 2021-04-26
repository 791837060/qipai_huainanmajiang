package com.anbang.qipai.qinyouquan.web.vo;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.Identity;

public class LianmengHehuoren {
    private String agentId;//推广员id
    private String memberId;//玩家id
    private String nickname;//推广员昵称
    private int xiajiCount;//联盟内玩家数
    private Identity identity;//联盟职位

    private boolean ban;//封禁

    private int juCount;                     //对局总数
    private int balance;                    //砖石余额
    private int lianmengmembercost;         //联盟成员砖石消耗

    private int threeDayJuCount;            //三日对局总数
    private int sevenDayJuCount;            //七日对局总数

    private int chengyuanCount;             //成员数量

    public int getChengyuanCount() {
        return chengyuanCount;
    }

    public void setChengyuanCount(int chengyuanCount) {
        this.chengyuanCount = chengyuanCount;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getXiajiCount() {
        return xiajiCount;
    }

    public void setXiajiCount(int xiajiCount) {
        this.xiajiCount = xiajiCount;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public int getJuCount() {
        return juCount;
    }

    public void setJuCount(int juCount) {
        this.juCount = juCount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getLianmengmembercost() {
        return lianmengmembercost;
    }

    public void setLianmengmembercost(int lianmengmembercost) {
        this.lianmengmembercost = lianmengmembercost;
    }

    public int getThreeDayJuCount() {
        return threeDayJuCount;
    }

    public void setThreeDayJuCount(int threeDayJuCount) {
        this.threeDayJuCount = threeDayJuCount;
    }

    public int getSevenDayJuCount() {
        return sevenDayJuCount;
    }

    public void setSevenDayJuCount(int sevenDayJuCount) {
        this.sevenDayJuCount = sevenDayJuCount;
    }
}
