package com.anbang.qipai.dalianmeng.cqrs.c.domain.score;

import com.dml.accounting.AccountOwner;

/**
 * 玩家积分账户持有人
 */
public class MemberScoreAccountOwner implements AccountOwner {

    private String memberId;

    private String lianmengId;

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
}
