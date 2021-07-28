package com.anbang.qipai.zongyangmajiang.plan.dao;

import com.anbang.qipai.zongyangmajiang.plan.bean.MemberGoldBalance;

public interface MemberGoldBalanceDao {

	void save(MemberGoldBalance memberGoldBalance);

	MemberGoldBalance findByMemberId(String memberId);
}
