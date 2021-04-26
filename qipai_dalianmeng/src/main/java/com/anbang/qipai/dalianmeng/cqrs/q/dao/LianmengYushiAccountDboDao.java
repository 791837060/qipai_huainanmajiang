package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.LianmengYushiAccountDbo;

public interface LianmengYushiAccountDboDao {

    void save(LianmengYushiAccountDbo account);

    void updateBalance(String id, int balance);

    LianmengYushiAccountDbo findById(String id);

    LianmengYushiAccountDbo findByAgentId(String agentId);
}
