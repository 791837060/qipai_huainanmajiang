package com.anbang.qipai.admin.plan.dao.mongodb.mongodbsignIndao;

import com.anbang.qipai.admin.plan.bean.ObatinSigningPrizeRecord;
import com.anbang.qipai.admin.plan.dao.signindao.ObtainSignPrizeRecordDao;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongodbObtainSignPrizeRecordDao implements ObtainSignPrizeRecordDao {

    private MongoTemplate template;

    @Override
    public void save(ObatinSigningPrizeRecord record) {
        this.template.save(record);
    }
}
