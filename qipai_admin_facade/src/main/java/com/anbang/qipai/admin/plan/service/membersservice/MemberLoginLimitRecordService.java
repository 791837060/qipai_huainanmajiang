package com.anbang.qipai.admin.plan.service.membersservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.members.MemberLoginLimitRecord;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberLoginLimitRecordDao;
import com.highto.framework.web.page.ListPage;

@Service
public class MemberLoginLimitRecordService {

	@Autowired
	private MemberLoginLimitRecordDao memberLoginLimitRecordDao;

	public void save(MemberLoginLimitRecord record) {
		memberLoginLimitRecordDao.save(record);
	}

	public void updateMemberLoginLimitRecordEfficientById(String[] ids, boolean efficient) {
		memberLoginLimitRecordDao.updateMemberLoginLimitRecordEfficientById(ids, efficient);
	}

	public ListPage findMemberLoginLimitRecordByMemberId(int page, int size, String memberId) {
		long amount = memberLoginLimitRecordDao.getAmountByMemberId(memberId);
		List<MemberLoginLimitRecord> recordList = memberLoginLimitRecordDao.findMemberLoginLimitRecordByMemberId(page,
				size, memberId);
		return new ListPage(recordList, page, size, (int) amount);
	}
}
