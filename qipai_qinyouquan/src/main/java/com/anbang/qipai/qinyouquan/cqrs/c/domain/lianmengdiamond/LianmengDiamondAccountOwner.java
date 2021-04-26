package com.anbang.qipai.qinyouquan.cqrs.c.domain.lianmengdiamond;

import com.dml.accounting.AccountOwner;

/**
 * 玩家联盟玉石账户持有人
 */
public class LianmengDiamondAccountOwner implements AccountOwner {

    private String mengzhuId;

    public String getMengzhuId() {
        return mengzhuId;
    }

    public void setMengzhuId(String mengzhuId) {
        this.mengzhuId = mengzhuId;
    }
}
