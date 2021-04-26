package com.anbang.qipai.doudizhu.plan.dao;

import com.anbang.qipai.doudizhu.plan.bean.MemberGoldBalance;

public interface MemberGoldBalanceDao {

	void save(MemberGoldBalance memberGoldBalance);

	MemberGoldBalance findByMemberId(String memberId);
}
