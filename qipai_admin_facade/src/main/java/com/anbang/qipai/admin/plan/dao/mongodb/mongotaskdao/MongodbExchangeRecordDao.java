package com.anbang.qipai.admin.plan.dao.mongodb.mongotaskdao;

import com.anbang.qipai.admin.plan.bean.tasks.ExchangeRecord;
import com.anbang.qipai.admin.plan.dao.tasksdao.ExchangeRecordDao;
import com.anbang.qipai.admin.web.query.TaskCommonQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yins
 * @Description: TODO
 */
@Component
public class MongodbExchangeRecordDao implements ExchangeRecordDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addExchangeRecord(ExchangeRecord exchangeRecord) {
        mongoTemplate.insert(exchangeRecord);
    }

    @Override
    public void updateExchangeRecord(ExchangeRecord exchangeRecord) {
        Query query = new Query(Criteria.where("id").is(exchangeRecord.getId()));
        Update update = new Update();
        update.set("id",exchangeRecord.getId());
        update.set("type",exchangeRecord.getExchangeType());
        update.set("nickName",exchangeRecord.getNickName());
        update.set("playerId",exchangeRecord.getPlayerId());
        update.set("loginIP",exchangeRecord.getLoginIP());
        update.set("exchangeTime",exchangeRecord.getExchangeTime());
        update.set("exchangeType",exchangeRecord.getExchangeType());
        update.set("exchangeAmount",exchangeRecord.getExchangeAmount());
        update.set("itemName",exchangeRecord.getItemName());
        update.set("status",exchangeRecord.getStatus());
        mongoTemplate.updateFirst(query,update,ExchangeRecord.class);
    }

    @Override
    public long countExchangeRecord(TaskCommonQuery taskCommonQuery) {
        Query query = new Query();
        if (taskCommonQuery != null) {
            if (StringUtils.isNotBlank(taskCommonQuery.getPlayerId())) {
                query.addCriteria(Criteria.where("playerId").regex(taskCommonQuery.getPlayerId()));
            }
            if (StringUtils.isNotBlank(taskCommonQuery.getLoginIP())) {
                query.addCriteria(Criteria.where("loginIP").is(taskCommonQuery.getLoginIP()));
            }
            if (taskCommonQuery.getStartTime() != null || taskCommonQuery.getEndTime() != null) {
                Criteria criteria = Criteria.where("exchangeTime");
                if (taskCommonQuery.getStartTime() != null) {
                    criteria = criteria.gte(taskCommonQuery.getStartTime());
                }
                if (taskCommonQuery.getEndTime() != null) {
                    criteria = criteria.lte(taskCommonQuery.getEndTime());
                }
                query.addCriteria(criteria);
            }
        }
        query.addCriteria(Criteria.where("status").is("SUCCESS"));
        return mongoTemplate.count(query, ExchangeRecord.class);
    }

    @Override
    public List<ExchangeRecord> getExchangeRecords(int page, int size, TaskCommonQuery taskCommonQuery) {
        Query query = new Query();
        if (taskCommonQuery != null) {
            if (StringUtils.isNotBlank(taskCommonQuery.getPlayerId())) {
                query.addCriteria(Criteria.where("playerId").regex(taskCommonQuery.getPlayerId()));
            }
            if (StringUtils.isNotBlank(taskCommonQuery.getLoginIP())) {
                query.addCriteria(Criteria.where("loginIP").is(taskCommonQuery.getLoginIP()));
            }
            if (taskCommonQuery.getStartTime() != null || taskCommonQuery.getEndTime() != null) {
                Criteria criteria = Criteria.where("exchangeTime");
                if (taskCommonQuery.getStartTime() != null) {
                    criteria = criteria.gte(taskCommonQuery.getStartTime());
                }
                if (taskCommonQuery.getEndTime() != null) {
                    criteria = criteria.lte(taskCommonQuery.getEndTime());
                }
                query.addCriteria(criteria);
            }
        }
        query.addCriteria(Criteria.where("status").is("SUCCESS"));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "exchangeTime")));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, ExchangeRecord.class);
    }
}
