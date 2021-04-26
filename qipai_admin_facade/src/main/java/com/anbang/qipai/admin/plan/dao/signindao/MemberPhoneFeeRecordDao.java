package com.anbang.qipai.admin.plan.dao.signindao;

import com.anbang.qipai.admin.plan.bean.signin.PhoneFeeRecord;

import java.util.List;

public interface MemberPhoneFeeRecordDao {

    void save(PhoneFeeRecord phoneFeeRecord);

    List<PhoneFeeRecord> findGoldRecordByMemberId(int page, int size, String memberId);

}
