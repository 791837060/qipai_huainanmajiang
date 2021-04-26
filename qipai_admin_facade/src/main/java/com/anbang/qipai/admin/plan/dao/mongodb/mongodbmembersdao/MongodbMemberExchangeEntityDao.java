package com.anbang.qipai.admin.plan.dao.mongodb.mongodbmembersdao;

import com.anbang.qipai.admin.plan.bean.members.MemberExchangeEntityDbo;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberExchangeEntityDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Author: 吴硕涵
 * @Date: 2018/12/25 2:56 PM
 * @Version 1.0
 */
@Component
public class MongodbMemberExchangeEntityDao implements MemberExchangeEntityDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void add(MemberExchangeEntityDbo dbo) {
        mongoTemplate.insert(dbo);
    }

    @Override
    public void save(MemberExchangeEntityDbo dbo) {
        mongoTemplate.save(dbo);
    }

    @Override
    public MemberExchangeEntityDbo findRecordByMemberIdAndRecordId(String memberId, String recordId) {
        Query query = new Query(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("raffleRecordId").is(recordId));
        return mongoTemplate.findOne(query, MemberExchangeEntityDbo.class);
    }

    @Override
    public List<MemberExchangeEntityDbo> findWithConditions(String memberId, String nickName, String telephone,
                                                            int page, int size, Long startTime, Long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(nickName)) {
            query.addCriteria(Criteria.where("nickName").is(nickName));
        }
        if (!StringUtils.isEmpty(telephone)) {
            query.addCriteria(Criteria.where("telephone").is(telephone));
        }
        if (startTime != null) {
            query.addCriteria(Criteria.where("exchangeTime").gte(startTime));
        }
        if (endTime != null) {
            query.addCriteria(Criteria.where("exchangeTime").lte(endTime));
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "exchangeTime"));
        query.with(sort);
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberExchangeEntityDbo.class);
    }

    @Override
    public int countWithConditions(String memberId, String nickName, String telephone, Long startTime, Long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(nickName)) {
            query.addCriteria(Criteria.where("nickName").is(nickName));
        }
        if (!StringUtils.isEmpty(telephone)) {
            query.addCriteria(Criteria.where("telephone").is(telephone));
        }
        if (startTime != null) {
            query.addCriteria(Criteria.where("exchangeTime").gte(startTime));
        }
        if (endTime != null) {
            query.addCriteria(Criteria.where("exchangeTime").lte(endTime));
        }

        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "exchangeTime"));
        query.with(sort);

        return (int) mongoTemplate.count(query, MemberExchangeEntityDbo.class);
    }

    @Override
    public MemberExchangeEntityDbo findById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, MemberExchangeEntityDbo.class);

    }


    @Override
    public int countPendingReward() {
        Query query = new Query(Criteria.where("hasExchange").is(false));
        return (int) mongoTemplate.count(query, MemberExchangeEntityDbo.class);

    }
}
