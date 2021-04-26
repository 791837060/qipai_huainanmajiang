package com.anbang.qipai.dalianmeng.cqrs.q.dao.mongodb;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.LianmengYushiAccountingRecordDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.LianmengYushiAccountingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class MongodbLianmengYushiAccountingRecordDao implements LianmengYushiAccountingRecordDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(LianmengYushiAccountingRecord record) {
        mongoTemplate.insert(record);
    }

    @Override
    public List<LianmengYushiAccountingRecord> findByAgentId(int page, int size, String mengzhuId, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(mengzhuId)) {
            query.addCriteria(Criteria.where("mengzhuId").is(mengzhuId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        query.with(new Sort(Sort.Direction.DESC, "accountingTime"));
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("accountingTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }
        if (page != 0 && size != 0) {
            query.skip((page - 1) * size);
            query.limit(size);
        }
        return mongoTemplate.find(query, LianmengYushiAccountingRecord.class);

    }

    @Override
    public long countByMemberIdAndLianmengId(String memberId, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("mengzhuId").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        query.with(new Sort(Sort.Direction.DESC, "accountingTime"));
        if (startTime > 0 || endTime > 0) {
            Criteria criteria = Criteria.where("accountingTime");
            if (startTime > 0) {
                criteria.gt(startTime);
            }
            if (endTime > 0) {
                criteria.lt(endTime);
            }
            query.addCriteria(criteria);
        }

        return mongoTemplate.count(query, LianmengYushiAccountingRecord.class);
    }

    @Override
    public void removeByTime(long endTime) {
        mongoTemplate.remove(new Query(Criteria.where("accountingTime").lt(endTime)), LianmengYushiAccountingRecord.class);
    }
}
