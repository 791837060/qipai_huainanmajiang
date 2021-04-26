package com.anbang.qipai.qinyouquan.cqrs.q.dao;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.LianmengDiamondAccountingRecord;

import java.util.List;

public interface LianmengDiamondAccountingRecordDao {

    void save(LianmengDiamondAccountingRecord record);

    List<LianmengDiamondAccountingRecord> findByAgentId(int page, int size, String mengzhuId, String lianmengId, long startTime, long endTime);

    long countByMemberIdAndLianmengId(String memberId, String lianmengId, long startTime, long endTime);

    long countByMemberIdAndLianmengId(String memberId, String lianmengId,long startTime,long endTime,boolean searchGameCost);

    List<LianmengDiamondAccountingRecord> findByMemberIdsAndLianmengId(int page, int size, String memberId, String lianmengId,
                                                                       long startTime, long endTime, boolean searchGameCost);


}
