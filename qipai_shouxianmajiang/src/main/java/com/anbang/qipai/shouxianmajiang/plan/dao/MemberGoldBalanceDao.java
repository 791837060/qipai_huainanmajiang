package com.anbang.qipai.shouxianmajiang.plan.dao;

import com.anbang.qipai.shouxianmajiang.plan.bean.MemberGoldBalance;


public interface MemberGoldBalanceDao {

	void save(MemberGoldBalance memberGoldBalance);

	MemberGoldBalance findByMemberId(String memberId);
}
