package com.anbang.qipai.maanshanmajiang.plan.dao;

import com.anbang.qipai.maanshanmajiang.plan.bean.MemberGoldBalance;

public interface MemberGoldBalanceDao {

	void save(MemberGoldBalance memberGoldBalance);

	MemberGoldBalance findByMemberId(String memberId);
}
