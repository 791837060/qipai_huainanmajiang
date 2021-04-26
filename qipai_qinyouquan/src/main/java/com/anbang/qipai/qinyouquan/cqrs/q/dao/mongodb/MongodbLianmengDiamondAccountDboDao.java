package com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.LianmengDiamondAccountDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.LianmengDiamondAccountDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDiamondAccountDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class MongodbLianmengDiamondAccountDboDao implements LianmengDiamondAccountDboDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(LianmengDiamondAccountDbo account) {
        mongoTemplate.insert(account);
    }

    @Override
    public void updateBalance(String id, int balance) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("balance", balance);
        mongoTemplate.updateFirst(query, update, LianmengDiamondAccountDbo.class);
    }

    @Override
    public LianmengDiamondAccountDbo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, LianmengDiamondAccountDbo.class);
    }

    @Override
    public LianmengDiamondAccountDbo findByAgentId(String agentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("agentId").is(agentId));
        return mongoTemplate.findOne(query, LianmengDiamondAccountDbo.class);
    }

    @Override
    public MemberDiamondAccountDbo findByMemberId(String memberId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        return mongoTemplate.findOne(query, MemberDiamondAccountDbo.class);
    }
}
