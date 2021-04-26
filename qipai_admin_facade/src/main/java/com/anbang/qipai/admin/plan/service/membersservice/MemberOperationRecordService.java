package com.anbang.qipai.admin.plan.service.membersservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.members.MemberOperationRecord;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberOperationRecordDao;
import com.highto.framework.web.page.ListPage;

@Service
public class MemberOperationRecordService {

	@Autowired
	private MemberOperationRecordDao memberOperationRecordDao;

    public void save(MemberOperationRecord record) {
        memberOperationRecordDao.save(record);
    }

	public ListPage findByConditions(int page, int size, MemberOperationRecord record) {
		long amount = memberOperationRecordDao.countAmountByConditions(record);
		List<MemberOperationRecord> recordList = memberOperationRecordDao.findByConditions(page, size, record);
		ListPage listPage = new ListPage(recordList, page, size, (int) amount);
		return listPage;
	}
}
