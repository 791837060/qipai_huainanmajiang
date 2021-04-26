package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.BoxAccountDbo;

public interface BoxAccountDboDao {
    void save(BoxAccountDbo account);

    BoxAccountDbo findById(String id);

    BoxAccountDbo findByMemberIdAndLianmengId(String memberId,String lianmengId);

    void updateBalance(String id, double balance);
}
