package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.PowerAccountingRecord;

import java.util.List;

public interface PowerAccountingRecordDao {
    void save(PowerAccountingRecord record);

    long countByMemberIdAndLianmengId(String memberId, String lianmengId,long startTime,long endTime,String summary);

    List<PowerAccountingRecord> findByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId,long startTime,long endTime,String summary);

    List<PowerAccountingRecord> findByMemberIdsAndLianmengId(int page, int size, List<String> memberIds, String lianmengId,long startTime,long endTime,String summary);

    PowerAccountingRecord findByMemberIdAndLianmengIdAndTime(String memberId,String lianmengId ,long startTime ,long endTime);

    void removeByTime(long endTime);

}
