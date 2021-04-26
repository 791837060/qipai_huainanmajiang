package com.anbang.qipai.qinyouquan.cqrs.q.dao;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.LianmengDiamondAccountDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDiamondAccountDbo;

public interface LianmengDiamondAccountDboDao {

    void save(LianmengDiamondAccountDbo account);

    void updateBalance(String id, int balance);

    LianmengDiamondAccountDbo findById(String id);

    LianmengDiamondAccountDbo findByAgentId(String agentId);

    MemberDiamondAccountDbo findByMemberId(String memberId);
}
