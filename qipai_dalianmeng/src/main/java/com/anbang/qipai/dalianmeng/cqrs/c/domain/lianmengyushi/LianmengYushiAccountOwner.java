package com.anbang.qipai.dalianmeng.cqrs.c.domain.lianmengyushi;

import com.dml.accounting.AccountOwner;

/**
 * 玩家联盟玉石账户持有人
 */
public class LianmengYushiAccountOwner implements AccountOwner {

    private String agentId;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
