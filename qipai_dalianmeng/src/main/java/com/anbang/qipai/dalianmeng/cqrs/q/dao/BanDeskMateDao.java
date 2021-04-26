package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.BanDeskMate;

import java.util.List;

public interface BanDeskMateDao {
    void save(BanDeskMate banDeskMate);

    BanDeskMate findById(String id);

    long countByLianmengIdAndMemberId(String lianmengId, String memberId,long queryTime);

    List<BanDeskMate> findByLianmengIdAndMemberId(int page, int size, String lianmengId, String memberId, long queryTime);

    void remove(String id, String lianmengId);

    BanDeskMate findByHehuorenId(String hehuorenId, String lianmengId);

    BanDeskMate findByMemberAIdAndMemberBId(String memberAId, String memberBId, String lianmengId);
}
