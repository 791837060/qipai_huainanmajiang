package com.anbang.qipai.admin.plan.dao.mongodb.mongodbsignIndao;

import com.anbang.qipai.admin.plan.bean.members.MemberGoldRecordDbo;
import com.anbang.qipai.admin.plan.bean.signin.PhoneFeeRecord;
import com.anbang.qipai.admin.plan.dao.signindao.MemberPhoneFeeRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbPhoneFeeRecordDao implements MemberPhoneFeeRecordDao {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void save(PhoneFeeRecord phoneFeeRecord) {
        this.mongoTemplate.save(phoneFeeRecord);
    }

    @Override
    public List<PhoneFeeRecord> findGoldRecordByMemberId(int page, int size, String memberId) {
        Query query = new Query(Criteria.where("memberId").is(memberId));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "accountingTime")));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, PhoneFeeRecord.class);
    }
}
