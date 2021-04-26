//package com.anbang.qipai.dalianmeng.plan.dao.mongodb;
//
//
//import com.anbang.qipai.dalianmeng.plan.dao.MemberDayHistoricalResultDao;
//import org.eclipse.jetty.util.StringUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class MongodbMemberDayHistoricalResultDao implements MemberDayHistoricalResultDao {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Override
//    public void save(MemberDayHistoricalResult result) {
//        mongoTemplate.insert(result);
//    }
//
//    @Override
//    public long countByLianmengIdAndRefererAndMemberIdAndTime(String lianmengId, String referer, String memberId, long startTime, long endTime) {
//        Query query = new Query();
//        if (StringUtil.isNotBlank(lianmengId)) {
//            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
//        }
//        if (StringUtil.isNotBlank(referer)) {
//            query.addCriteria(Criteria.where("referer").is(referer));
//        }
//        if (StringUtil.isNotBlank(memberId)) {
//            query.addCriteria(Criteria.where("playerId").is(memberId));
//        }
//        query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
//        return mongoTemplate.count(query, MemberDayHistoricalResult.class);
//    }
//
//    @Override
//    public List<MemberDayHistoricalResult> findByLianmengIdAndRefererAndMemberIdAndTime(int page, int size, String lianmengId, String referer,
//                                                                                        String memberId, long startTime, long endTime) {
//        Query query = new Query();
//        if (StringUtil.isNotBlank(lianmengId)) {
//            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
//        }
//        if (StringUtil.isNotBlank(referer)) {
//            query.addCriteria(Criteria.where("referer").is(referer));
//        }
//        if (StringUtil.isNotBlank(memberId)) {
//            query.addCriteria(Criteria.where("playerId").is(memberId));
//        }
//        query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
//        query.skip((page - 1) * size);
//        query.limit(size);
//        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "dayingjiaCount")));
//        return mongoTemplate.find(query, MemberDayHistoricalResult.class);
//    }
//
//    @Override
//    public MemberDayHistoricalResult findByLianmengIdAndRefererAndPlayerIdAndTime(String lianmengId, String referer, String playerId, long startTime, long endTime) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
//        query.addCriteria(Criteria.where("referer").is(referer));
//        query.addCriteria(Criteria.where("playerId").is(playerId));
//        query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
//        return mongoTemplate.findOne(query, MemberDayHistoricalResult.class);
//    }
//
//    @Override
//    public void updateIncById(String id, int dayingjiaCount, int juCount, double yushiCost, double totalScore, long createTime) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("id").is(id));
//        Update update = new Update();
//        update.inc("dayingjiaCount", dayingjiaCount);
//        update.inc("finishJuCount", juCount);
//        update.inc("yushiCost", yushiCost);
//        update.inc("totalScore", totalScore);
//        update.set("createTime", createTime);
//        mongoTemplate.updateFirst(query, update, MemberDayHistoricalResult.class);
//    }
//
//    @Override
//    public void removeByTime(long endTime) {
//        mongoTemplate.remove(new Query(Criteria.where("createTime").lt(endTime)), MemberDayHistoricalResult.class);
//    }
//
//}
