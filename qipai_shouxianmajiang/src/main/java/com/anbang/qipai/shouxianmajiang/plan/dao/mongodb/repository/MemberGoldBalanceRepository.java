package com.anbang.qipai.shouxianmajiang.plan.dao.mongodb.repository;

import com.anbang.qipai.shouxianmajiang.plan.bean.MemberGoldBalance;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface MemberGoldBalanceRepository extends MongoRepository<MemberGoldBalance, String> {

	MemberGoldBalance findOneByMemberId(String memberId);
}
