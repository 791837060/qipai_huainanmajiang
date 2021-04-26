package com.anbang.qipai.qinyouquan.cqrs.q.dao;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDiamondAccountingRecord;

import java.util.List;

public interface MemberDiamondAccountingRecordDao {
    void save(MemberDiamondAccountingRecord record);

    long countByMemberIdAndLianmengId(String memberId, String lianmengId,long startTime,long endTime);

    List<MemberDiamondAccountingRecord> findByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId, long startTime, long endTime);

    List<MemberDiamondAccountingRecord> findByMemberIdsAndLianmengId(int page, int size, List<String> memberIds, String lianmengId, long startTime, long endTime);

    MemberDiamondAccountingRecord findByMemberIdAndLianmengIdAndTime(String memberId, String lianmengId , long startTime , long endTime);

    long countByMemberIdAndLianmengId(List<String> memberIds, String lianmengId,long startTime,long endTime,boolean searchGameCost);

    List<MemberDiamondAccountingRecord> findByMemberIdsAndLianmengId(int page, int size, List<String> memberIds, String lianmengId,
                                                                     long startTime,long endTime,boolean searchGameCost);

}
