package com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberDiamondAccountingRecordDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDiamondAccountingRecord;
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
public class MongodbMemberDiamondAccountingRecordDao implements MemberDiamondAccountingRecordDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void save(MemberDiamondAccountingRecord record) {
        mongoTemplate.insert(record);
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
        return mongoTemplate.count(query, MemberDiamondAccountingRecord.class);
    }

    @Override
    public List<MemberDiamondAccountingRecord> findByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId, long startTime, long endTime) {
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
        return mongoTemplate.find(query, MemberDiamondAccountingRecord.class);
    }

    @Override
    public List<MemberDiamondAccountingRecord> findByMemberIdsAndLianmengId(int page, int size, List<String> memberIds, String lianmengId, long startTime, long endTime) {
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
        query.with(new Sort(Sort.Direction.DESC, "accountingTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberDiamondAccountingRecord.class);
    }

    @Override
    public MemberDiamondAccountingRecord findByMemberIdAndLianmengIdAndTime(String memberId, String lianmengId , long startTime , long endTime) {
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
        return mongoTemplate.findOne(query, MemberDiamondAccountingRecord.class);
    }


    @Override
    public long countByMemberIdAndLianmengId(List<String> memberIds, String lianmengId, long startTime, long endTime, boolean searchGameCost) {
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
        if (searchGameCost){
            query.addCriteria(Criteria.where("summary.text").is("game ju finish"));
        }else {
            query.addCriteria(Criteria.where("summary.text").ne("game ju finish"));
        }
        return mongoTemplate.count(query, MemberDiamondAccountingRecord.class);
    }

    @Override
    public List<MemberDiamondAccountingRecord> findByMemberIdsAndLianmengId(int page, int size, List<String> memberIds,
                                                                            String lianmengId, long startTime, long endTime, boolean searchGameCost) {
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
        if (searchGameCost){
            query.addCriteria(Criteria.where("summary.text").is("game ju finish"));
        }else {
            query.addCriteria(Criteria.where("summary.text").ne("game ju finish"));
        }
        query.with(new Sort(Sort.Direction.DESC, "accountingTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberDiamondAccountingRecord.class);
    }

}
