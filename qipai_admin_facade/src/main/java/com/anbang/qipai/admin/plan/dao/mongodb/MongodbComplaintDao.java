package com.anbang.qipai.admin.plan.dao.mongodb;


import com.anbang.qipai.admin.plan.bean.Complaint;
import com.anbang.qipai.admin.plan.dao.ComplaintDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;


@Component
public class MongodbComplaintDao implements ComplaintDao {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void save(Complaint complaint) {
        mongoTemplate.insert(complaint);
    }

    @Override
    public long countBymemberId(String memberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        query.with(new Sort(Sort.Direction.DESC, "complaintTime"));
        return mongoTemplate.count(query, Complaint.class);
    }

    @Override
    public List<Complaint> find(int page, int size, String memberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        query.with(new Sort(Sort.Direction.DESC, "complaintTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, Complaint.class);
    }
}
