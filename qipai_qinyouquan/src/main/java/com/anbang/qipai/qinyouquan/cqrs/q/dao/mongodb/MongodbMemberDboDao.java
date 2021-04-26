package com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class MongodbMemberDboDao implements MemberDboDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MemberDbo dbo) {
        mongoTemplate.insert(dbo);
    }

    @Override
    public MemberDbo findById(String memberId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").regex(memberId));
        return mongoTemplate.findOne(query, MemberDbo.class);
    }

    @Override
    public void updateMember(String memberId, String nickname, String headimgurl) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(memberId));
        Update update = new Update();
        update.set("nickname", nickname);
        update.set("headimgurl", headimgurl);
        mongoTemplate.updateFirst(query, update, MemberDbo.class);
    }

    @Override
    public MemberDbo findByNickname(String nickname) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nickname").regex(nickname));
        return mongoTemplate.findOne(query, MemberDbo.class);
    }

    @Override
    public void updateMemberDalianmengApply(String memberId, boolean dalianmeng, boolean qinyouquan) {
        Query query = new Query(Criteria.where("id").is(memberId));
        Update update = new Update();
        update.set("dalianmeng", dalianmeng);
        update.set("qinyouquan", qinyouquan);
        mongoTemplate.updateFirst(query, update, MemberDbo.class);
    }

}
