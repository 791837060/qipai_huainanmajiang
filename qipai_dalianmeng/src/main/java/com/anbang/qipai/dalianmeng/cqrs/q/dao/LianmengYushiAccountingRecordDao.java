package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.LianmengYushiAccountingRecord;

import java.util.List;

public interface LianmengYushiAccountingRecordDao {

    void save(LianmengYushiAccountingRecord record);

    List<LianmengYushiAccountingRecord> findByAgentId(int page, int size, String mengzhuId, String lianmengId, long startTime, long endTime);

    long countByMemberIdAndLianmengId(String memberId, String lianmengId, long startTime, long endTime);

    void removeByTime(long endTime);

}
