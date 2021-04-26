package com.anbang.qipai.admin.plan.dao.mongodb.mongotaskdao;

import com.anbang.qipai.admin.plan.bean.tasks.TaskReceiveRecord;
import com.anbang.qipai.admin.plan.dao.tasksdao.TaskReceiveRecordDao;
import com.anbang.qipai.admin.web.query.TaskCommonQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yins
 * @Description: TODO
 */
@Component
public class MongodbTaskReceiveRecordDao implements TaskReceiveRecordDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addTaskReceiveRecord(TaskReceiveRecord taskReceiveRecord) {
        mongoTemplate.save(taskReceiveRecord);
    }

    @Override
    public long countTaskReceiveRecord(TaskCommonQuery taskCommonQuery) {
        Query query = new Query();
        if(taskCommonQuery != null) {
            if (StringUtils.isNotBlank(taskCommonQuery.getPlayerId())) {
                query.addCriteria(Criteria.where("playerId").regex(taskCommonQuery.getPlayerId()));
            }
            if (StringUtils.isNotBlank(taskCommonQuery.getLoginIP())) {
                query.addCriteria(Criteria.where("loginIP").is(taskCommonQuery.getLoginIP()));
            }
            if (taskCommonQuery.getStartTime() != null || taskCommonQuery.getEndTime() != null) {
                Criteria criteria = Criteria.where("receiveTime");
                if (taskCommonQuery.getStartTime() != null) {
                    criteria = criteria.gte(taskCommonQuery.getStartTime());
                }
                if (taskCommonQuery.getEndTime() != null) {
                    criteria = criteria.lte(taskCommonQuery.getEndTime());
                }
                query.addCriteria(criteria);
            }
        }
        return mongoTemplate.count(query, TaskReceiveRecord.class);
    }

    @Override
    public List<TaskReceiveRecord> getTaskReceiveRecords(int page, int size, TaskCommonQuery taskCommonQuery) {
        Query query = new Query();
        if (taskCommonQuery != null) {
            if (StringUtils.isNotBlank(taskCommonQuery.getPlayerId())) {
                query.addCriteria(Criteria.where("playerId").regex(taskCommonQuery.getPlayerId()));
            }
            if (StringUtils.isNotBlank(taskCommonQuery.getLoginIP())) {
                query.addCriteria(Criteria.where("loginIP").is(taskCommonQuery.getLoginIP()));
            }
            if (taskCommonQuery.getStartTime() != null || taskCommonQuery.getEndTime() != null) {
                Criteria criteria = Criteria.where("receiveTime");
                if (taskCommonQuery.getStartTime() != null) {
                    criteria = criteria.gte(taskCommonQuery.getStartTime());
                }
                if (taskCommonQuery.getEndTime() != null) {
                    criteria = criteria.lte(taskCommonQuery.getEndTime());
                }
                query.addCriteria(criteria);
            }
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "receiveTime")));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, TaskReceiveRecord.class);
    }
}
