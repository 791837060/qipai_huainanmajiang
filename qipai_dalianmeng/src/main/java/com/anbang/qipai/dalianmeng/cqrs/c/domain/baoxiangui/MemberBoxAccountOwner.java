package com.anbang.qipai.dalianmeng.cqrs.c.domain.baoxiangui;

import com.dml.accounting.AccountOwner;

/**
 * 玩家保险柜账户持有人
 */
public class MemberBoxAccountOwner implements AccountOwner {

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
