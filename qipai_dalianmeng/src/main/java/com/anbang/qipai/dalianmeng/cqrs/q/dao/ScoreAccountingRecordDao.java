package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.ScoreAccountingRecord;

import java.util.List;

public interface ScoreAccountingRecordDao {
    void save(ScoreAccountingRecord record);

    int countByLianmengIdAndTime(String lianmengId, long startTime, long endTime);

    int countByLianmengIdAndRefererAndTime(String lianmengId, String referer, long startTime, long endTime);

    long countByMemberIdAndLianmengId(String memberId, String lianmengId, long startTime, long endTime);

    List<ScoreAccountingRecord> findByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId, long startTime, long endTime);

    int countScoreCostByLianmengIdAndRefererAndTime(String lianmengId,String referer,long startTime,long endTime);

    void removeByTime(long endTime);
}
