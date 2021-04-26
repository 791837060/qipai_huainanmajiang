package com.anbang.qipai.admin.plan.service.signinservice;

import com.anbang.qipai.admin.plan.bean.signin.PhoneFeeRecord;
import com.anbang.qipai.admin.plan.dao.signindao.MemberPhoneFeeRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneFeeRecordService {

    @Autowired
    private MemberPhoneFeeRecordDao memberPhoneFeeRecordDao;

    public void save(PhoneFeeRecord record) {
        this.memberPhoneFeeRecordDao.save(record);
    }

    public List<PhoneFeeRecord> findByMemberId(int page, int size, String memberId) {
        return this.memberPhoneFeeRecordDao.findGoldRecordByMemberId(page, size, memberId);
    }

}
