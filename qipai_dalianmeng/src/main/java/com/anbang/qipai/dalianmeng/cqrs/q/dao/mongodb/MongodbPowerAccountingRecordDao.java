package com.anbang.qipai.dalianmeng.cqrs.q.dao.mongodb;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.PowerAccountingRecordDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.PowerAccountingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/11/11 11:11
 */
@Component
public class MongodbPowerAccountingRecordDao implements PowerAccountingRecordDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void save(PowerAccountingRecord record) {
        mongoTemplate.insert(record);
    }


    @Override
    public long countByMemberIdAndLianmengId(String memberId, String lianmengId, long startTime, long endTime, String summary) {
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
        if (!StringUtils.isEmpty(summary)) {
            query.addCriteria(Criteria.where("summary.text").ne(summary));
        }
        return mongoTemplate.count(query, PowerAccountingRecord.class);
    }

    @Override
    public List<PowerAccountingRecord> findByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId, long startTime, long endTime, String summary) {
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
        if (!StringUtils.isEmpty(summary)) {
            query.addCriteria(Criteria.where("summary.text").ne(summary));
        }
        query.with(new Sort(Sort.Direction.DESC, "accountingTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, PowerAccountingRecord.class);
    }

    @Override
    public List<PowerAccountingRecord> findByMemberIdsAndLianmengId(int page, int size, List<String> memberIds, String lianmengId, long startTime, long endTime, String summary) {
        Query query = new Query();
        if (!memberIds.isEmpty()){
            query.addCriteria(Criteria.where("memberId").in(memberIds));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("accountingTime").gt(startTime).lt(endTime));
        }
        if (!StringUtils.isEmpty(summary)) {
            query.addCriteria(Criteria.where("summary.text").ne(summary));
        }
        query.with(new Sort(Sort.Direction.DESC, "accountingTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, PowerAccountingRecord.class);
    }

    @Override
    public PowerAccountingRecord findByMemberIdAndLianmengIdAndTime(String memberId,String lianmengId ,long startTime ,long endTime) {
        Query query = new Query();
        if (!memberId.isEmpty()){
            query.addCriteria(Criteria.where("memberId").in(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("accountingTime").gt(startTime).lt(endTime));
        }
        query.with(new Sort(Sort.Direction.DESC, "accountingTime"));
        return mongoTemplate.findOne(query, PowerAccountingRecord.class);
    }

    @Override
    public void removeByTime(long endTime) {
        mongoTemplate.remove(new Query(Criteria.where("accountingTime").lt(endTime)), PowerAccountingRecord.class);
    }

}
