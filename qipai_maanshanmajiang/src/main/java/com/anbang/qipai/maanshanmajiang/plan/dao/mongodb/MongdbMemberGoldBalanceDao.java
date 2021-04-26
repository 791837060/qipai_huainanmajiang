package com.anbang.qipai.maanshanmajiang.plan.dao.mongodb;

import com.anbang.qipai.maanshanmajiang.plan.bean.MemberGoldBalance;
import com.anbang.qipai.maanshanmajiang.plan.dao.MemberGoldBalanceDao;
import com.anbang.qipai.maanshanmajiang.plan.dao.mongodb.repository.MemberGoldBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongdbMemberGoldBalanceDao implements MemberGoldBalanceDao {

	@Autowired
	private MemberGoldBalanceRepository memberGoldBalanceRepository;

	@Override
	public void save(MemberGoldBalance memberGoldBalance) {
		memberGoldBalanceRepository.save(memberGoldBalance);
	}

	@Override
	public MemberGoldBalance findByMemberId(String memberId) {
		return memberGoldBalanceRepository.findOneByMemberId(memberId);
	}

}
