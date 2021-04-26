package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.MemberApplyingRecord;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.MemberDbo;

import java.util.List;

public interface MemberApplyingRecordDao {

    void save(MemberApplyingRecord applyingRecord);

    void updateStateAndAuditorById(String id, MemberDbo memberDbo, String state);

    void removeById(String id);

    MemberApplyingRecord findById(String id);

    List<MemberApplyingRecord> findByMemberIdAndLianmengAndIdentity(String memberId, String lianmengId);

    long countByStateAndLianmengIdAndIdentity(String state, String lianmengId, long queryTime);

    List<MemberApplyingRecord> findByStateAndLianmengIdAndIdentity(int page, int size, String state, String lianmengId, long queryTime);

    long countByNotStateAndLianmengId(String state, String lianmengId, long queryTime);

    List<MemberApplyingRecord> findByNotStateAndLianmengId(int page, int size, String state, String lianmengId, long queryTime);
}
