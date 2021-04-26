package com.anbang.qipai.tuidaohu.plan.dao;

import com.anbang.qipai.tuidaohu.plan.bean.MemberGoldBalance;

public interface MemberGoldBalanceDao {

	void save(MemberGoldBalance memberGoldBalance);

	MemberGoldBalance findByMemberId(String memberId);
}
