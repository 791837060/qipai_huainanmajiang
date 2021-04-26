package com.anbang.qipai.guandan.plan.dao;

import com.anbang.qipai.guandan.plan.bean.MemberGoldBalance;

public interface MemberGoldBalanceDao {

	void save(MemberGoldBalance memberGoldBalance);

	MemberGoldBalance findByMemberId(String memberId);

}
