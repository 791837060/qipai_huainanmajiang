package com.anbang.qipai.admin.plan.dao.membersdao;

import com.anbang.qipai.admin.plan.bean.members.MemberExchangeEntityDbo;

import java.util.List;

/**
 * @Author: 吴硕涵
 * @Date: 2018/12/25 2:52 PM
 * @Version 1.0
 */
public interface MemberExchangeEntityDao {
    void add(MemberExchangeEntityDbo dbo);
    void save(MemberExchangeEntityDbo dbo);

    MemberExchangeEntityDbo findById(String id);

    int countWithConditions(String memberId, String nickName, String telephone, Long startTime, Long endTime);

    MemberExchangeEntityDbo findRecordByMemberIdAndRecordId(String memberId,String recordId);

    List<MemberExchangeEntityDbo> findWithConditions(String memberId, String nickName, String telephone, int page, int size, Long startTime, Long endTime);

    int countPendingReward();
}
