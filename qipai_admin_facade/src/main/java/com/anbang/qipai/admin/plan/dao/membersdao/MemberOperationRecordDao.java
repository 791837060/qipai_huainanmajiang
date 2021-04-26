package com.anbang.qipai.admin.plan.dao.membersdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.members.MemberOperationRecord;

public interface MemberOperationRecordDao {

	void save(MemberOperationRecord record);

	List<MemberOperationRecord> findByConditions(int page, int size, MemberOperationRecord record);

	long countAmountByConditions(MemberOperationRecord record);
}
