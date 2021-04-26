package com.anbang.qipai.admin.plan.dao.membersdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.members.MemberLoginLimitRecord;

public interface MemberLoginLimitRecordDao {

	void save(MemberLoginLimitRecord record);

	void updateMemberLoginLimitRecordEfficientById(String[] ids, boolean efficient);

	List<MemberLoginLimitRecord> findMemberLoginLimitRecordByMemberId(int page, int size, String memberId);

	long getAmountByMemberId(String memberId);
}
