package com.anbang.qipai.qinyouquan.plan.dao.mongodb;

import com.anbang.qipai.qinyouquan.plan.bean.MemberDayResultData;
import com.anbang.qipai.qinyouquan.plan.dao.MemberDayResultDataDao;
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
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));

        return mongoTemplate.findOne(query, MemberDayResultData.class);
    }

    @Override
    public List<MemberDayResultData> findByMemberIdAndLianmengIdAndTime(int page ,int size,List<String> memberId, String lianmengId,
                                                                        long startTime, long endTime, String diamondCostSort,
                                                                        String juCountSort, String zhanjiCountSort, String dayingjiaCountSort, String diamondSort) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").in(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        if (!StringUtils.isEmpty(diamondCostSort)){
            if (diamondCostSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "diamondCost")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "diamondCost")));
            }
        }
        if (!StringUtils.isEmpty(juCountSort)){
            if (juCountSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "juCount")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "juCount")));
            }
        }
        if (!StringUtils.isEmpty(zhanjiCountSort)){
            if (zhanjiCountSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "totalScore")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "totalScore")));
            }
        }
        if (!StringUtils.isEmpty(dayingjiaCountSort)){
            if (dayingjiaCountSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "dayingjiaCount")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "dayingjiaCount")));
            }
        }
        if (!StringUtils.isEmpty(diamondSort)){
            if (diamondSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "diamond")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "diamond")));
            }
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberDayResultData.class);
    }

    @Override
    public long countByMemberIdAndLianmengIdAndTime(List<String> memberIds, String lianmengId, long startTime, long endTime, String diamondCostSort, String juCountSort, String zhanjiCountSort, String dayingjiaCountSort, String diamondSort) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").in(memberIds));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        if (!StringUtils.isEmpty(diamondCostSort)){
            if (diamondCostSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "diamondCost")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "diamondCost")));
            }
        }
        if (!StringUtils.isEmpty(juCountSort)){
            if (juCountSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "juCount")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "juCount")));
            }
        }
        if (!StringUtils.isEmpty(zhanjiCountSort)){
            if (zhanjiCountSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "totalScore")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "totalScore")));
            }
        }
        if (!StringUtils.isEmpty(dayingjiaCountSort)){
            if (dayingjiaCountSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "dayingjiaCount")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "dayingjiaCount")));
            }
        }
        if (!StringUtils.isEmpty(diamondSort)){
            if (diamondSort.equals("DESC")){
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "diamond")));
            }else {
                query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "diamond")));
            }
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        return mongoTemplate.count(query, MemberDayResultData.class);
    }

    @Override
    public List<MemberDayResultData> findByLianmengIdAndTime(int page,int size,String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
        query.with(sort);
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, MemberDayResultData.class);
    }

    @Override
    public long getAmountByLianmengIdAndTime(String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        return mongoTemplate.count(query, MemberDayResultData.class);
    }



    @Override
    public void deleteByTime(long startTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lt(startTime));
        mongoTemplate.remove(query,MemberDayResultData.class);
    }

    @Override
    public void deleteByMemberIdAndLianmengId(String memberId, String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
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
    public void increaseDiamondCost(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("diamondCost",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberDiamondCost(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberDiamondCost",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberTotalScore(String memberId, String lianmengId, long startTime, long endTime, double amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberTotalScore",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseTotalScore(String memberId, String lianmengId, long startTime, long endTime, double amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("totalScore",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseFinishJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("finishJuCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseDiamond(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("diamond",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseMemberDiamond(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("memberDiamond",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public void increaseDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("memberId").is(memberId));
        if (startTime!=0&&endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("dayingjiaCount",amount);
        mongoTemplate.updateMulti(query, update,MemberDayResultData.class);
    }

    @Override
    public MemberDayResultData findByMembersIdAndLianmengIdAndTime(String memberId, String playerId, String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)){
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)){
            query.addCriteria(Criteria.where("memberId").is(memberId));
        }
        if (!StringUtils.isEmpty(playerId)){
            query.addCriteria(Criteria.where("memberId").is(playerId));
        }
        if (startTime!=0 && endTime!=0){
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));

        return mongoTemplate.findOne(query, MemberDayResultData.class);
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
}
