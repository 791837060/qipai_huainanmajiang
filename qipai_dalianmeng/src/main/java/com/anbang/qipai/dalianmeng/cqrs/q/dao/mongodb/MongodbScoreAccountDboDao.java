package com.anbang.qipai.dalianmeng.cqrs.q.dao.mongodb;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.ScoreAccountDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.ScoreAccountDbo;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MongodbScoreAccountDboDao implements ScoreAccountDboDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(ScoreAccountDbo account) {
        mongoTemplate.insert(account);
    }

    @Override
    public void updateBalance(String id, double balance,double totalScore) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("balance", balance);
        update.set("totalScore", totalScore);
        mongoTemplate.updateFirst(query, update, ScoreAccountDbo.class);
    }

    @Override
    public ScoreAccountDbo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, ScoreAccountDbo.class);
    }

    @Override
    public ScoreAccountDbo findByMemberIdAndLianmengId(String memberId, String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.findOne(query, ScoreAccountDbo.class);
    }

    @Override
    public int countBalanceByLianmengId(String lianmengId) {
        List<DBObject> pipeline = new ArrayList<>();
        BasicDBObject match = new BasicDBObject();
        if (!StringUtils.isEmpty(lianmengId)) {
            match.put("lianmengId", lianmengId);
        }
        DBObject queryMatch = new BasicDBObject("$match", match);
        pipeline.add(queryMatch);

        BasicDBObject group = new BasicDBObject();
        group.put("_id", null);
        group.put("num", new BasicDBObject("$sum", "balance"));
        DBObject queryGroup = new BasicDBObject("$group", group);
        pipeline.add(queryGroup);
        Cursor cursor = mongoTemplate.getCollection("scoreAccountDbo").aggregate(pipeline,
                AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
        try {
            return (int) cursor.next().get("num");
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<ScoreAccountDbo> findBylianmemngId(String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.find(query,ScoreAccountDbo.class);
    }
}
