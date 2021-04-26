package com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberApplyingRecordDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberApplyingRecord;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class MongodbMemberApplyingRecordDao implements MemberApplyingRecordDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MemberApplyingRecord applyingRecord) {
        mongoTemplate.insert(applyingRecord);
    }

    @Override
    public void updateStateAndAuditorById(String id, MemberDbo memberDbo, String state) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("auditorMemberId", memberDbo.getId());
        update.set("auditorMemberNickname", memberDbo.getNickname());
        update.set("auditorMemberHeadimgurl", memberDbo.getHeadimgurl());
        update.set("state", state);
        mongoTemplate.updateFirst(query, update, MemberApplyingRecord.class);
    }

    @Override
    public void removeById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, MemberApplyingRecord.class);
    }

    @Override
    public MemberApplyingRecord findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, MemberApplyingRecord.class);
    }

    @Override
    public List<MemberApplyingRecord> findByMemberIdAndLianmengAndIdentity(String memberId, String lianmengId ) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        return mongoTemplate.find(query, MemberApplyingRecord.class);
    }


    @Override
    public long countByStateAndLianmengIdAndIdentity(String state, String lianmengId,  long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(state)) {
            query.addCriteria(Criteria.where("state").is(state));
        }
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        return mongoTemplate.count(query, MemberApplyingRecord.class);
    }

    @Override
    public List<MemberApplyingRecord> findByStateAndLianmengIdAndIdentity(int page, int size, String state, String lianmengId, long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(state)) {
            query.addCriteria(Criteria.where("state").is(state));
        }
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberApplyingRecord.class);
    }

    @Override
    public long countByNotStateAndLianmengId(String state, String lianmengId,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(state)) {
            query.addCriteria(Criteria.where("state").ne(state));
        }
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        return mongoTemplate.count(query, MemberApplyingRecord.class);
    }

    @Override
    public List<MemberApplyingRecord> findByNotStateAndLianmengId(int page, int size, String state, String lianmengId,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(state)) {
            query.addCriteria(Criteria.where("state").ne(state));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        return mongoTemplate.find(query, MemberApplyingRecord.class);
    }
}
