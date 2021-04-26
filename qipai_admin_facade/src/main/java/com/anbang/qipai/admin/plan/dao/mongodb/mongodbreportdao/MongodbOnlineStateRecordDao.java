package com.anbang.qipai.admin.plan.dao.mongodb.mongodbreportdao;

import com.anbang.qipai.admin.plan.bean.report.OnlineStateRecord;
import com.anbang.qipai.admin.plan.dao.reportdao.OnlineStateRecordDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author YaphetS
 * @date 2018/11/24
 */
@Repository
public class MongodbOnlineStateRecordDao implements OnlineStateRecordDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insert(OnlineStateRecord onlineStateRecord) {
        mongoTemplate.insert(onlineStateRecord);
    }

    @Override
    public long countOnlineRecord() {
        Query query = new Query(where("onlineState").is(0));
        return mongoTemplate.count(query, OnlineStateRecord.class);
    }

    @Override
    public long countOnlineRecordAfterTime(long createTime) {
        BasicDBObject query = new BasicDBObject();
        query.put("createTime", new BasicDBObject("$gte", createTime));
        query.put("onlineState",0);
        return mongoTemplate.getCollection("onlineStateRecord").distinct("memberId", query).size();
    }

    @Override
    public List<OnlineStateRecord> findOnlineRecordAfterTime(long dayStartTime) {
        Query query=new Query(where("createTime").gte(dayStartTime));
        return mongoTemplate.find(query,OnlineStateRecord.class);
    }

    @Override
    public List<OnlineStateRecord> findByMemberIdAfterTime(String memberId, Long createTime) {
        return mongoTemplate.find(query(where("memberId").is(memberId)
                .and("createTime").gte(createTime)), OnlineStateRecord.class);
    }

    @Override
    public OnlineStateRecord lastRecord(String id) {
        Query query=new Query();
        query.addCriteria(Criteria.where("memberId").is(id));
        query.addCriteria(Criteria.where("onlineState").is(0));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        return mongoTemplate.findOne(query,OnlineStateRecord.class);
    }

    @Override
    public List<String> listIdsByTime(long startTime, long endTime) {
        Aggregation aggregation = Aggregation.newAggregation(OnlineStateRecord.class,
                Aggregation.match(Criteria.where("createTime").gt(startTime).lt(endTime)),
                Aggregation.group("memberId"));
        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(aggregation, OnlineStateRecord.class,
                BasicDBObject.class);
        List<BasicDBObject> dbObjects = result.getMappedResults();
        List<String> ids = dbObjects.stream().map(p -> p.getString("_id")).collect(Collectors.toList());
        return ids;
    }

}
