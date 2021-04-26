package com.anbang.qipai.qinyouquan.cqrs.c.domain.memberdiamond;

import com.dml.accounting.AccountOwner;

/**
 * 玩家能量账户持有人
 */
public class MemberDiamondAccountOwner implements AccountOwner {

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
