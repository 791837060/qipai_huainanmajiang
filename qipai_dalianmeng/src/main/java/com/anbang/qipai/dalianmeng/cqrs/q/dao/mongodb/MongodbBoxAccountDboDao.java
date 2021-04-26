package com.anbang.qipai.dalianmeng.cqrs.q.dao.mongodb;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.BoxAccountDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.BoxAccountDbo;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.PowerAccountDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class MongodbBoxAccountDboDao implements BoxAccountDboDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(BoxAccountDbo account) {
        mongoTemplate.insert(account);
    }

    @Override
    public BoxAccountDbo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, BoxAccountDbo.class);
    }

    @Override
    public BoxAccountDbo findByMemberIdAndLianmengId(String memberId, String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.findOne(query, BoxAccountDbo.class);
    }

    @Override
    public void updateBalance(String id, double balance) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("balance", balance);
        mongoTemplate.updateFirst(query, update, BoxAccountDbo.class);
    }
}
