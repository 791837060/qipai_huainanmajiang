package com.anbang.qipai.admin.plan.service.signinservice;

import com.anbang.qipai.admin.plan.bean.ObatinSigningPrizeRecord;
import com.anbang.qipai.admin.plan.dao.signindao.ObtainSignPrizeRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObtainSignPrizeRecordService {

    @Autowired
    private ObtainSignPrizeRecordDao obtainSignPrizeRecordDao;

    public void save(ObatinSigningPrizeRecord record) {
        this.obtainSignPrizeRecordDao.save(record);
    }

}
