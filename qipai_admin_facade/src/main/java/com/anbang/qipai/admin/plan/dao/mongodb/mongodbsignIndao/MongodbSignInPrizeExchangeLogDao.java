package com.anbang.qipai.admin.plan.dao.mongodb.mongodbsignIndao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeExchangeLog;
import com.anbang.qipai.admin.plan.dao.signindao.SignInPrizeExchangeLogDao;

/**
 * @author created by hanzhuofan 2018.09.15
 */
@Component
public class MongodbSignInPrizeExchangeLogDao implements SignInPrizeExchangeLogDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public SignInPrizeExchangeLog findBymemberId(String memberId) {
        Query query = new Query(Criteria.where("memberId").is(memberId));
        return mongoTemplate.findOne(query, SignInPrizeExchangeLog.class);
    }


    @Override
    public void addSignInPrizeExchangeLog(SignInPrizeExchangeLog signInPrizeExchangeLog) {
        mongoTemplate.save(signInPrizeExchangeLog);
    }

    @Override
    public List<SignInPrizeExchangeLog> querySignInPrizeExchangeLog(SignInPrizeExchangeLog signInPrizeExchangeLog,
                                                                    Long startTime, Long endTime) {
        Query query = new Query();
        if (signInPrizeExchangeLog.getMemberId() != null) {
            query.addCriteria(Criteria.where("memberId").is(signInPrizeExchangeLog.getMemberId()));
        }
        if (signInPrizeExchangeLog.getNickName() != null) {
            query.addCriteria(Criteria.where("nickname").is(signInPrizeExchangeLog.getNickName()));
        }
        if (signInPrizeExchangeLog.getPhone() != null) {
            query.addCriteria(Criteria.where("phone").is(signInPrizeExchangeLog.getPhone()));
        }
        if (startTime != null) {
            query.addCriteria(Criteria.where("rewardTime").gte(startTime));
        }
        if (endTime != null) {
            query.addCriteria(Criteria.where("rewardTime").lte(endTime));
        }
        return mongoTemplate.find(query, SignInPrizeExchangeLog.class);
    }

    @Override
    public void issueSignInPrize(int id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").lte(id));
        Update update = Update.update("issue", "1").set("issueTime", System.currentTimeMillis());
        mongoTemplate.upsert(query, update, SignInPrizeExchangeLog.class);
    }

    @Override
    public long countUnIssueSignInPrize() {
        Query query = new Query(Criteria.where("issue").is("0"));
        return mongoTemplate.count(query, SignInPrizeExchangeLog.class);
    }

    @Override
    public void updateSignInPrizeExchangeLog(SignInPrizeExchangeLog signInPrizeExchangeLog) {
        Query query = new Query(Criteria.where("signInPrizeLog.id").is(signInPrizeExchangeLog.getId()));
        Update update = new Update();
        update.set("phone", signInPrizeExchangeLog.getPhone());
        update.set("deliveryName", signInPrizeExchangeLog.getDeliveryName());
        update.set("address", signInPrizeExchangeLog.getAddress());
        update.set("rewardTime", System.currentTimeMillis());
        mongoTemplate.updateFirst(query, update, SignInPrizeExchangeLog.class);
    }

}
