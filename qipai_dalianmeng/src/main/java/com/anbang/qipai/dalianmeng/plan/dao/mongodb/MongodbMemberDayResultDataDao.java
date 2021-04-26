package com.anbang.qipai.dalianmeng.plan.dao.mongodb;

import com.anbang.qipai.dalianmeng.plan.bean.MemberDayResultData;
import com.anbang.qipai.dalianmeng.plan.dao.MemberDayResultDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class MongodbMemberDayResultDataDao implements MemberDayResultDataDao {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void save(MemberDayResultData memberDayResultData) {
        mongoTemplate.insert(memberDayResultData);
    }

    @Override
    public MemberDayResultData findByMemberIdAndLianmengIdAndTime(String memberId, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }

        return mongoTemplate.findOne(query, MemberDayResultData.class);
    }

    @Override
    public List<MemberDayResultData> findByMemberIdAndLianmengIdAndTime(int page , int size, List<String> memberId, String lianmengId,
                                                                        long startTime, long endTime, String powerSort, String scoreSort, String juCountSort,
                                                                        String dayingjiaCountSort,
                                                                        String powerCostSort ) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").in(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        if (!StringUtils.isEmpty(powerSort)){
            if (powerSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "power")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "power")));
            }
        }
        if (!StringUtils.isEmpty(scoreSort)){
            if (scoreSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "score")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "score")));
            }
        }
        if (!StringUtils.isEmpty(juCountSort)){
            if (juCountSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "juCount")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "juCount")));
            }
        }
        if (!StringUtils.isEmpty(dayingjiaCountSort)){
            if (dayingjiaCountSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "dayingjiaCount")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "dayingjiaCount")));
            }
        }
        if (!StringUtils.isEmpty(powerCostSort)){
            if (powerCostSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "powerCost")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "powerCost")));
            }
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberDayResultData.class);
    }

    @Override
    public long countByMemberIdAndLianmengIdAndTime(List<String> memberIds, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").in(memberIds));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        return mongoTemplate.count(query, MemberDayResultData.class);
    }

    @Override
    public void increaseDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int dayingjiaCount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("dayingjiaCount",dayingjiaCount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseJuCount(String memberId, String lianmengId, long startTime, long endTime, int juCount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("juCount",juCount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void updatePowerCost(String memberId, String lianmengId, long startTime, long endTime, double power) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("powerCost",power);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void updatePower(String memberId, String lianmengId, long startTime, long endTime, double power) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("power",power);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void updateMemberPower(String memberId, String lianmengId, long startTime, long endTime, double power) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberPower",power);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void updateScore(String memberId, String lianmengId, long startTime, long endTime, double score) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("score",score);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }
    @Override
    public void updateTotalScore(String memberId, String lianmengId, long startTime, long endTime, double score) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("totalScore",score);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void deleteByTime(long startTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lt(startTime));
        mongoTemplate.remove(query,MemberDayResultData.class);
    }

    @Override
    public void increaseErrenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("errenDayingjiaCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberErrenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberErrenDayingjiaCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseSanrenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("sanrenDayingjiaCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberSanrenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberSanrenDayingjiaCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseSirenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("sirenDayingjiaCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberSirenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberSirenDayingjiaCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseDuorenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("duorenDayingjiaCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberDuorenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberDuorenDayingjiaCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }


    @Override
    public void increaseErrenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("errenJuCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberErrenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberErrenJuCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseSanrenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("sanrenJuCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberSanrenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberSanrenJuCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseSirenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("sirenJuCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberSirenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberSirenJuCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseDuorenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("duorenJuCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberDuorenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberDuorenJuCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void updateMemberPowerCost(String memberId, String lianmengId, long startTime, long endTime, double power) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberPowerCost",power);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public List<MemberDayResultData> findAllByMemberIdAndLianmengIdAndTime(String memberId, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }

        return mongoTemplate.find(query, MemberDayResultData.class);
    }

}
