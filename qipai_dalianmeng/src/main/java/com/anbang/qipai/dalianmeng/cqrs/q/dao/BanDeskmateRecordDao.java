package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.BanDeskmateRecord;

import java.util.List;

public interface BanDeskmateRecordDao {
    void save(BanDeskmateRecord banDeskmateRecord);

    long countAll();

    List<BanDeskmateRecord> findAll(int page ,int size);

    long countByLianmengIdAndMemberId(String lianmengId ,String memberId);

    List<BanDeskmateRecord> findByLianmengIdAndMemberId(int page , int size , String lianmengId , String memberId);

    void remove(String id);
}
