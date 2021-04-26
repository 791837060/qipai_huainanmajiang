package com.anbang.qipai.members.plan.service;

import com.anbang.qipai.members.plan.bean.MemberLoginIPLimit;
import com.anbang.qipai.members.plan.dao.MemberLoginIPLimitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highto.framework.web.page.ListPage;

import java.util.List;

@Service
public class MemberLoginIPLimitService {

	@Autowired
	private MemberLoginIPLimitDao memberLoginIPLimitDao;

	public void save(MemberLoginIPLimit record) {
		memberLoginIPLimitDao.save(record);
	}

	public void updateMemberLoginLimitRecordEfficientById(String[] ids, boolean efficient) {
		memberLoginIPLimitDao.updateMemberLoginLimitRecordEfficientById(ids, efficient);
	}

	public ListPage findMemberLoginLimitRecordByLoginIp(int page, int size, String loginIp) {
		long amount = memberLoginIPLimitDao.getAmountByLoginIp(loginIp);
		List<MemberLoginIPLimit> recordList = memberLoginIPLimitDao.findMemberLoginLimitRecordByLoginIp(page,
				size, loginIp);
		return new ListPage(recordList, page, size, (int) amount);
	}

	public MemberLoginIPLimit getByLoginIp(String loginIp) {
		return memberLoginIPLimitDao.getByLoginIp(loginIp);
	}
}
