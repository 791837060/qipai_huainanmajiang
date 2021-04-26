package com.anbang.qipai.qinyouquan.cqrs.q.dao;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDiamondAccountDbo;

import java.util.List;

public interface MemberDiamondAccountDboDao {
    void save(MemberDiamondAccountDbo account);

    MemberDiamondAccountDbo findById(String id);

    MemberDiamondAccountDbo findByMemberIdAndLianmengId(String memberId, String lianmengId);

    void updateBalance(String id, double balance );

    List<MemberDiamondAccountDbo> findBylianmengId(String lianmengId);
}
