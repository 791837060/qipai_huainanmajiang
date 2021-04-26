package com.anbang.qipai.dalianmeng.cqrs.q.dao.mongodb;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.ScoreAccountingRecordDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.ScoreAccountingRecord;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Component
public class MongodbScoreAccountingRecordDao implements ScoreAccountingRecordDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(ScoreAccountingRecord record) {
        mongoTemplate.insert(record);
    }

    @Override
    public int countByLianmengIdAndTime(String lianmengId, long startTime, long endTime) {
        List<DBObject> pipeline = new ArrayList<>();
        BasicDBObject match = new BasicDBObject();
        BasicDBObject criteria = new BasicDBObject();
        if (startTime > 0) {
            criteria.put("$gt", startTime);
        }
        if (endTime > 0) {
            criteria.put("$lt", endTime);
        }
        if (startTime > 0 || endTime > 0) {
            match.put("accountingTime", criteria);
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            match.put("lianmengId", lianmengId);
        }
        match.put("accountAmount", new BasicDBObject("$lt", 0));
        DBObject queryMatch = new BasicDBObject("$match", match);
        pipeline.add(queryMatch);

        BasicDBObject group = new BasicDBObject();
        group.put("_id", null);
        group.put("num", new BasicDBObject("$sum", "$accountAmount"));
        DBObject queryGroup = new BasicDBObject("$group", group);
        pipeline.add(queryGroup);
        Cursor cursor = mongoTemplate.getCollection("scoreAccountingRecord").aggregate(pipeline,
                AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
        try {
            return (int) cursor.next().get("num");
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int countByLianmengIdAndRefererAndTime(String lianmengId, String referer, long startTime, long endTime) {
        List<DBObject> pipeline = new ArrayList<>();
        BasicDBObject match = new BasicDBObject();
        BasicDBObject criteria = new BasicDBObject();
        if (startTime > 0) {
            criteria.put("$gt", startTime);
        }
        if (endTime > 0) {
            criteria.put("$lt", endTime);
        }
        if (startTime > 0 || endTime > 0) {
            match.put("accountingTime", criteria);
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            match.put("lianmengId", lianmengId);
        }
        if (!StringUtils.isEmpty(referer)) {
            match.put("referer", referer);
        }
        match.put("accountAmount", new BasicDBObject("$lt", 0));
        DBObject queryMatch = new BasicDBObject("$match", match);
        pipeline.add(queryMatch);

        BasicDBObject group = new BasicDBObject();
        group.put("_id", null);
        group.put("num", new BasicDBObject("$sum", "$accountAmount"));
        DBObject queryGroup = new BasicDBObject("$group", group);
        pipeline.add(queryGroup);
        Cursor cursor = mongoTemplate.getCollection("scoreAccountingRecord").aggregate(pipeline,
                AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
        try {
            return (int) cursor.next().get("num");
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long countByMemberIdAndLianmengId(String memberId, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("accountingTime").gt(startTime).lt(endTime));
        }
        query.with(new Sort(Sort.Direction.DESC, "accountingTime"));
        return mongoTemplate.count(query, ScoreAccountingRecord.class);
    }

    @Override
    public List<ScoreAccountingRecord> findByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("accountingTime").gt(startTime).lt(endTime));
        }
        query.with(new Sort(Sort.Direction.DESC, "accountingTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, ScoreAccountingRecord.class);
    }

    @Override
    public int countScoreCostByLianmengIdAndRefererAndTime(String lianmengId, String referer, long startTime, long endTime) {
        List<DBObject> pipeline = new ArrayList<>();
        BasicDBObject match = new BasicDBObject();
        BasicDBObject criteria = new BasicDBObject();
        if (startTime > 0) {
            criteria.put("$gt", startTime);
        }
        if (endTime > 0) {
            criteria.put("$lt", endTime);
        }
        match.put("accountingTime", criteria);
        if (!StringUtils.isEmpty(lianmengId)) {
            match.put("lianmengId", lianmengId);
        }
        if (!StringUtils.isEmpty(referer)) {
            match.put("referer", referer);
        }
        match.put("accountAmount", new BasicDBObject("$lt", 0));
        DBObject queryMatch = new BasicDBObject("$match", match);
        pipeline.add(queryMatch);

        BasicDBObject group = new BasicDBObject();
        group.put("_id", null);
        group.put("num", new BasicDBObject("$sum", "$accountAmount"));
        DBObject queryGroup = new BasicDBObject("$group", group);
        pipeline.add(queryGroup);
        Cursor cursor = mongoTemplate.getCollection("scoreAccountingRecord").aggregate(pipeline,
                AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
        try {
            return (int) cursor.next().get("num");
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void removeByTime(long endTime) {
        mongoTemplate.remove(new Query(Criteria.where("accountingTime").lt(endTime)), ScoreAccountingRecord.class);
    }


}
