package com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.LianmengDiamondAccountingRecordDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.LianmengDiamondAccountingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class MongodbLianmengDiamondAccountingRecordDao implements LianmengDiamondAccountingRecordDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(LianmengDiamondAccountingRecord record) {
        mongoTemplate.insert(record);
    }

    @Override
    public List<LianmengDiamondAccountingRecord> findByAgentId(int page, int size, String mengzhuId, String lianmengId, long startTime, long endTime) {
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
        return mongoTemplate.find(query, LianmengDiamondAccountingRecord.class);

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

        return mongoTemplate.count(query, LianmengDiamondAccountingRecord.class);
    }

    @Override
    public long countByMemberIdAndLianmengId(String memberId, String lianmengId, long startTime, long endTime, boolean searchGameCost) {
        Query query = new Query();
        if (!memberId.isEmpty()){
            query.addCriteria(Criteria.where("mengzhuId").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)&&searchGameCost) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("accountingTime").gt(startTime).lt(endTime));
        }
        if (searchGameCost){
            query.addCriteria(Criteria.where("summary.text").is("game ju finish"));
        }else {
            query.addCriteria(Criteria.where("summary.text").ne("game ju finish"));
        }
        return mongoTemplate.count(query, LianmengDiamondAccountingRecord.class);
    }

    @Override
    public List<LianmengDiamondAccountingRecord> findByMemberIdsAndLianmengId(int page, int size, String memberId,
                                                                              String lianmengId, long startTime, long endTime, boolean searchGameCost) {
        Query query = new Query();
        if (!memberId.isEmpty()){
            query.addCriteria(Criteria.where("mengzhuId").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)&&searchGameCost) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("accountingTime").gt(startTime).lt(endTime));
        }
        if (searchGameCost){
            query.addCriteria(Criteria.where("summary.text").is("game ju finish"));
        }else {
            query.addCriteria(Criteria.where("summary.text").ne("game ju finish"));
        }
        query.with(new Sort(Sort.Direction.DESC, "accountingTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, LianmengDiamondAccountingRecord.class);
    }
}
