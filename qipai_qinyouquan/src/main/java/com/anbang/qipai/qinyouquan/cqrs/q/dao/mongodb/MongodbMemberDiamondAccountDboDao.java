package com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberDiamondAccountDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDiamondAccountDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbMemberDiamondAccountDboDao implements MemberDiamondAccountDboDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MemberDiamondAccountDbo account) {
        mongoTemplate.insert(account);
    }

    @Override
    public MemberDiamondAccountDbo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, MemberDiamondAccountDbo.class);
    }

    @Override
    public MemberDiamondAccountDbo findByMemberIdAndLianmengId(String memberId, String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.findOne(query, MemberDiamondAccountDbo.class);
    }

    @Override
    public void updateBalance(String id, double balance) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("balance", balance);
        mongoTemplate.updateFirst(query, update, MemberDiamondAccountDbo.class);
    }

    @Override
    public List<MemberDiamondAccountDbo> findBylianmengId(String lianmengId) {
        Query query=new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.find(query, MemberDiamondAccountDbo.class);
    }
}
