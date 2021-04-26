package com.anbang.qipai.dalianmeng.web.vo;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.Identity;

public class LianmengHehuoren {
    private String agentId;//推广员id
    private String memberId;//玩家id
    private String nickname;//推广员昵称
    private int xiajiCount;//联盟内玩家数
    private Identity identity;//联盟职位

    private boolean ban;//封禁

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
}
