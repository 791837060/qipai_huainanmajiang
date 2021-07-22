package com.anbang.qipai.huainanmajiang.plan.dao;

import com.anbang.qipai.huainanmajiang.plan.bean.MemberGoldBalance;

public interface MemberGoldBalanceDao {

	void save(MemberGoldBalance memberGoldBalance);

	MemberGoldBalance findByMemberId(String memberId);
}
