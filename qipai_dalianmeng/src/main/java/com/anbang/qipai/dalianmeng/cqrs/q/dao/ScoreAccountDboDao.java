package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.ScoreAccountDbo;

import java.util.List;

public interface ScoreAccountDboDao {

    void save(ScoreAccountDbo account);

    void updateBalance(String id, double balance,double totalScore);

    ScoreAccountDbo findById(String id);

    ScoreAccountDbo findByMemberIdAndLianmengId(String memberId, String lianmengId);

    int countBalanceByLianmengId(String lianmengId);

    List<ScoreAccountDbo> findBylianmemngId(String lianmengId);


}
