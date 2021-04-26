package com.anbang.qipai.dalianmeng.cqrs.q.dao.mongodb;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.PowerAccountDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.PowerAccountDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbPowerAccountDboDao implements PowerAccountDboDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(PowerAccountDbo account) {
        mongoTemplate.insert(account);
    }

    @Override
    public PowerAccountDbo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, PowerAccountDbo.class);
    }

    @Override
    public PowerAccountDbo findByMemberIdAndLianmengId(String memberId, String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.findOne(query, PowerAccountDbo.class);
    }

    @Override
    public void updateBalance(String id, double balance) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("balance", balance);
        mongoTemplate.updateFirst(query, update, PowerAccountDbo.class);
    }

    @Override
    public List<PowerAccountDbo> findBylianmengId(String lianmengId) {
        Query query=new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.find(query,PowerAccountDbo.class);
    }
}
