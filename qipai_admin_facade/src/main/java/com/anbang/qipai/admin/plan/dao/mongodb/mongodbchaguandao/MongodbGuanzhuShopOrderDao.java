package com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao;

import com.anbang.qipai.admin.plan.bean.chaguan.GuanzhuShopOrder;
import com.anbang.qipai.admin.plan.dao.chaguandao.GuanzhuShopOrderDao;
import com.anbang.qipai.admin.web.query.GuanzhuOrderQuery;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ethan
 */
@Component
public class MongodbGuanzhuShopOrderDao implements GuanzhuShopOrderDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(GuanzhuShopOrder guanzhuShopOrder) {
        mongoTemplate.save(guanzhuShopOrder);
    }

    @Override
    public void updateAgentTotalSale(String id, double agentTotalSale) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("agentTotalSale", agentTotalSale);
        mongoTemplate.updateFirst(query, update, GuanzhuShopOrder.class);
    }

    @Override
    public int countByQuery(GuanzhuOrderQuery orderQuery) {
        Query query = new Query();
        if(StringUtils.isNotBlank(orderQuery.getPayerId())) {
            query.addCriteria(Criteria.where("payerId"));
        }
        if(StringUtils.isNotBlank(orderQuery.getPayerName())) {
            query.addCriteria(Criteria.where("payerName"));
        }
        if(StringUtils.isNotBlank(orderQuery.getReceiverId())) {
            query.addCriteria(Criteria.where("receiverId"));
        }
        if(StringUtils.isNotBlank(orderQuery.getReceiverName())) {
            query.addCriteria(Criteria.where("receiverName"));
        }
        query.addCriteria(Criteria.where("status").is("SUCCESS"));

        return (int) mongoTemplate.count(query, GuanzhuShopOrder.class);
    }

    @Override
    public List<GuanzhuShopOrder> listByQuery(GuanzhuOrderQuery orderQuery, int page, int size) {
        Query query = new Query();
        if(StringUtils.isNotBlank(orderQuery.getPayerId())) {
            query.addCriteria(Criteria.where("payerId"));
        }
        if(StringUtils.isNotBlank(orderQuery.getPayerName())) {
            query.addCriteria(Criteria.where("payerName"));
        }
        if(StringUtils.isNotBlank(orderQuery.getReceiverId())) {
            query.addCriteria(Criteria.where("receiverId"));
        }
        if(StringUtils.isNotBlank(orderQuery.getReceiverName())) {
            query.addCriteria(Criteria.where("receiverName"));
        }

        query.addCriteria(Criteria.where("status").is("SUCCESS"));
        if (orderQuery.getSort() != null) {
            query.with(orderQuery.getSort());
        }

        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, GuanzhuShopOrder.class);
    }

    @Override
    public double agentTotalSale(String agentId) {
        List<DBObject> pipeline = new ArrayList<>();
        BasicDBObject match = new BasicDBObject();
        match.put("receiverId", agentId);
        match.put("status", "SUCCESS");
        DBObject queryMatch = new BasicDBObject("$match", match);
        pipeline.add(queryMatch);

        BasicDBObject group = new BasicDBObject();
        group.put("_id", null);
        group.put("num", new BasicDBObject("$sum", "$totalamount"));
        DBObject queryGroup = new BasicDBObject("$group", group);
        pipeline.add(queryGroup);
        Cursor cursor = mongoTemplate.getCollection("guanzhuShopOrder").aggregate(pipeline,
                AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
        try {
            return (double) cursor.next().get("num");
        } catch (Exception e) {
            return 0;
        }
    }
}
