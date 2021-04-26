package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.PowerAccountDbo;

import java.util.List;

public interface PowerAccountDboDao {
    void save(PowerAccountDbo account);

    PowerAccountDbo findById(String id);

    PowerAccountDbo findByMemberIdAndLianmengId(String memberId, String lianmengId);

    void updateBalance(String id, double balance );

    List<PowerAccountDbo> findBylianmengId(String lianmengId);
}
