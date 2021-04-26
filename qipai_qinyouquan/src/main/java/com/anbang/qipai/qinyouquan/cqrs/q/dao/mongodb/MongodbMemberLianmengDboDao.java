package com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberLianmengDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.Identity;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberLianmengDbo;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MongodbMemberLianmengDboDao implements MemberLianmengDboDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MemberLianmengDbo memberLianmengDbo) {
        mongoTemplate.insert(memberLianmengDbo);
    }

    @Override
    public MemberLianmengDbo findByMemberIdAndLianmengId(String memberId, String lianmengId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        return mongoTemplate.findOne(query, MemberLianmengDbo.class);
    }

    @Override
    public MemberLianmengDbo findByMemberIdAndLianmengIdAndIdentity(String memberId, String lianmengId, Identity identity) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        return mongoTemplate.findOne(query, MemberLianmengDbo.class);
    }

    @Override
    public MemberLianmengDbo findByAgentIdAndLianmengIdAndIdentity(String agentId, String lianmengId, Identity identity) {
        Query query = new Query();
        if (!StringUtils.isEmpty(agentId)) {
            query.addCriteria(Criteria.where("agentId").is(agentId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        return mongoTemplate.findOne(query, MemberLianmengDbo.class);
    }

    @Override
    public void updateSuperiorMember(String memberId, String lianmengId, String superiorMemberId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        Update update = new Update();
        update.set("superiorMemberId", superiorMemberId);
        mongoTemplate.updateFirst(query, update, MemberLianmengDbo.class);
    }

    @Override
    public void updateContributionProportion(String memberId, String lianmengId, int contributionProportion) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        Update update = new Update();
        update.set("contributionProportion", contributionProportion);
        mongoTemplate.updateFirst(query, update, MemberLianmengDbo.class);
    }

    @Override
    public void updateOnlineState(String memberId, String onlineState) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        Update update = new Update();
        update.set("onlineState", onlineState);
        mongoTemplate.updateMulti(query, update, MemberLianmengDbo.class);
    }

    @Override
    public void updateIdentity(String memberId, String lianmengId, Identity identity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        Update update = new Update();
        update.set("identity", identity);
        mongoTemplate.updateFirst(query, update, MemberLianmengDbo.class);
    }


    @Override
    public long countByLianmengIdAndReferer(String lianmengId, String referer) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(referer)) {
            query.addCriteria(Criteria.where("referer").is(referer));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }

    @Override
    public long countByMemberIdAndIdentity(String memberId, Identity identity) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }





    @Override
    public List<MemberLianmengDbo> findByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }


    @Override
    public List<MemberLianmengDbo> findByMemberId(String memberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findByNicknameOrMemberIdAndLianmengId(int page, int size, String nickname,String lianmengId,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)&&!StringUtils.isEmpty(nickname)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId).orOperator(new Criteria("memberId").is(nickname),(Criteria.where("nickname").regex(nickname))));
        }
        if (queryTime != 0) {
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findByMemberIdAndIdentity(String memberId, Identity identity) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public long countByLianmengId(String lianmengId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }

    @Override
    public long countByLianmengIdAndIdentity(String lianmengId, Identity identity) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }

    @Override
    public long countByLianmengIdAndIdentity(String lianmengId, Identity identity,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findByLianmengIdAndIdentity(int page, int size, String lianmengId, Identity identity) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }


    @Override
    public long countOnlineMemberByLianmengId(String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("onlineState").is("online"));
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findByLianmengId(int page, int size, String lianmengId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        query.limit(size);
        query.skip((page - 1) * size);
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }


    @Override
    public void removeByLianmengId(String lianmengId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        mongoTemplate.remove(query, MemberLianmengDbo.class);
    }


    @Override
    public void removeByRefererAndLianmengId(String referer, String lianmengId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(referer)) {
            query.addCriteria(Criteria.where("referer").is(referer));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        mongoTemplate.remove(query, MemberLianmengDbo.class);
    }

    @Override
    public void updateBanByMemberIdAndLianmengId(String memberId, String lianmengId, boolean ban) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        Update update = new Update();
        update.set("ban", ban);
        mongoTemplate.updateFirst(query, update, MemberLianmengDbo.class);
    }

    @Override
    public void updateBanByRefererAndLianmengId(String referer, String lianmengId, boolean ban) {
        Query query = new Query();
        if (!StringUtils.isEmpty(referer)) {
            query.addCriteria(Criteria.where("referer").is(referer));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        Update update = new Update();
        update.set("ban", ban);
        mongoTemplate.updateMulti(query, update, MemberLianmengDbo.class);
    }


    @Override
    public long countrenshu(String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }

    @Override
    public long countrenshu(String lianmengId, long queryTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        if (queryTime != 0) {
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }




    @Override
    public List<MemberLianmengDbo> findByMemberIdAndLianmengIdAndIdentity1(String memeberId, String lianmengId, Identity identity) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memeberId)) {
            query.addCriteria(Criteria.where("memberId").is(memeberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }


    @Override
    public void removeByMemberIdAndLianmengId(String memberId, String lianmengId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        mongoTemplate.remove(query, MemberLianmengDbo.class);
    }



    @Override
    public List<MemberLianmengDbo> findByLianmengIdAndIdentity(String lianmengId, Identity identity) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findOnlineMemberByLianmengId(String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("onlineState").is("online"));
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public void updateSuperiorMemberIdAndIdentity(String memberId, String lianmengId, String superiorMemberId ) {
        Query query = new Query();
        query.addCriteria(Criteria.where("superiorMemberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        Update update = new Update();
        update.set("superiorMemberId", superiorMemberId);
        mongoTemplate.updateMulti(query, update, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findAll() {
        return mongoTemplate.findAll(MemberLianmengDbo.class);
    }

    @Override
    public void updateZhushouId(String memberId, String lianmengId, String zhushouId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        Update update = new Update();
        update.set("zhushouId", zhushouId);
        mongoTemplate.updateFirst(query, update, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findByLianmengIdAndSuperiorMemberIdAndIdentity(String lianmengId,String  superiorMemberId,Identity identity) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(superiorMemberId));
        }
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public long countByNicknameOrMemberIdAndLianmengId(String nickname,String lianmengId,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)&&!StringUtils.isEmpty(nickname)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId).orOperator(new Criteria("memberId").is(nickname),(Criteria.where("nickname").regex(nickname))));
        }
        if (queryTime != 0) {
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findByMemberIdAndLianmengIdAndSuperior(int page,int size,String lianmengId, String superiorMemberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(superiorMemberId));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public long getAmountByMemberIdAndLianmengIdAndSuperior(String lianmengId, String superiorMemberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(superiorMemberId));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }

    @Override
    public long getAmountByMemberIdAndLianmengIdAndSuperior(String lianmengId, String superiorMemberId,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(superiorMemberId));
        }
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findByMemberIdAndLianmengIdAndSuperior(String lianmengId, String superiorMemberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(superiorMemberId));
        }
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public void updateFree(String memberId, String lianmengId, boolean free) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        Update update = new Update();
        update.set("free", free);
        mongoTemplate.updateFirst(query, update, MemberLianmengDbo.class);
    }

    @Override
    public void updateDayScoreLimit(String memberId, String lianmengId, int maxScore,int minScore) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        Update update = new Update();
        update.set("maxScore", maxScore);
        update.set("minScore", minScore);

        mongoTemplate.updateFirst(query, update, MemberLianmengDbo.class);
    }

    @Override
    public void updateMemberScore(String memberId, String lianmengId, double score) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        Update update = new Update();
        update.set("score", score);
        mongoTemplate.updateFirst(query, update, MemberLianmengDbo.class);
    }

    @Override
    public long countByLianmengIdAndZhushouId(String lianmengId, String zhushouId,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(zhushouId)) {
            query.addCriteria(Criteria.where("zhushouId").is(zhushouId));
        }
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);

    }

    @Override
    public List<MemberLianmengDbo> findByLianmengIdAndZhushouId(int page, int size, String lianmengId, String zhushouId,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(zhushouId)) {
            query.addCriteria(Criteria.where("zhushouId").is(zhushouId));
            query.addCriteria(Criteria.where("superiorMemberId").is(zhushouId));
        }
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        query.limit(size);
        query.skip((page - 1) * size);
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findByLianmengIdAndSuperiorMemberIdAndIdentity(int skip, int size, String lianmengId, String superiorMemberId , Identity identity,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(superiorMemberId));
            query.addCriteria(Criteria.where("zhushouId").ne(superiorMemberId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        query.limit(size);
        query.skip(skip);
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public List<MemberLianmengDbo> findByMemberIdAndLianmengIdAndSuperior(int page, int size, String lianmengId, String superiorMemberId, String onlineSort) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(superiorMemberId));
        }
        if (!StringUtils.isEmpty(onlineSort)){
            if (onlineSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "onlineState")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "onlineState")));
            }
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public List<String> listIdsByLianmengIdAndSuperiorMemberId(String lianmengId, String superiorMemberId) {
        Aggregation aggregation = Aggregation.newAggregation(MemberLianmengDbo.class,
                Aggregation.match(Criteria.where("lianmengId").is(lianmengId).andOperator(Criteria.where("superiorMemberId").is(superiorMemberId))));
        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(aggregation, MemberLianmengDbo.class,
                BasicDBObject.class);
        List<BasicDBObject> dbObjects = result.getMappedResults();
        List<String> ids = dbObjects.stream().map(p -> p.getString("memberId")).collect(Collectors.toList());
        return ids;
    }

    @Override
    public List<MemberLianmengDbo> findByLianmengsIdAndIdentity(int page, int size, String playerId, String lianmengId, Identity identity) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(playerId)) {
            query.addCriteria(Criteria.where("memberId").is(playerId));
        }
        if (!StringUtils.isEmpty(identity)) {
            query.addCriteria(Criteria.where("identity").is(identity));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberLianmengDbo.class);
    }

    @Override
    public long getxiajiCountByLianmengIdAndIdentity(String lianmengId, String superiorMemberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(superiorMemberId)) {
            query.addCriteria(Criteria.where("superiorMemberId").is(superiorMemberId));
        }
        return mongoTemplate.count(query, MemberLianmengDbo.class);
    }

}
