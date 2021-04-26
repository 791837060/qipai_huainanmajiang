package com.anbang.qipai.dalianmeng.cqrs.q.dao.mongodb;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.LianmengYushiAccountDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.LianmengYushiAccountDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class MongodbLianmengYushiAccountDboDao implements LianmengYushiAccountDboDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(LianmengYushiAccountDbo account) {
        mongoTemplate.insert(account);
    }

    @Override
    public void updateBalance(String id, int balance) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("balance", balance);
        mongoTemplate.updateFirst(query, update, LianmengYushiAccountDbo.class);
    }

    @Override
    public LianmengYushiAccountDbo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, LianmengYushiAccountDbo.class);
    }

    @Override
    public LianmengYushiAccountDbo findByAgentId(String agentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("agentId").is(agentId));
        return mongoTemplate.findOne(query, LianmengYushiAccountDbo.class);
    }
}
