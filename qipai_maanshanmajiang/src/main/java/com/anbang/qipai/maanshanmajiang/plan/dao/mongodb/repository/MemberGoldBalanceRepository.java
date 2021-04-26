package com.anbang.qipai.maanshanmajiang.plan.dao.mongodb.repository;

import com.anbang.qipai.maanshanmajiang.plan.bean.MemberGoldBalance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberGoldBalanceRepository extends MongoRepository<MemberGoldBalance, String> {

	MemberGoldBalance findOneByMemberId(String memberId);
}
