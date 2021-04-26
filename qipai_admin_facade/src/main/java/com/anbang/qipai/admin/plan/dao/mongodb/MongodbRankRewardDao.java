package com.anbang.qipai.admin.plan.dao.mongodb;

import com.anbang.qipai.admin.plan.bean.rank.RankRewardDO;
import com.anbang.qipai.admin.plan.bean.rank.RankType;
import com.anbang.qipai.admin.plan.dao.rankdao.RankRewardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbRankRewardDao implements RankRewardDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(RankRewardDO rankRewardDO) {
        mongoTemplate.save(rankRewardDO);
    }

    @Override
    public void removeById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, RankRewardDO.class);
    }

    @Override
    public void removeByType(RankType rankType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(rankType));
        mongoTemplate.remove(query, RankRewardDO.class);
    }

    @Override
    public void updateRemain(String id, int remain) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("remain", remain);
        mongoTemplate.remove(query, RankRewardDO.class);
    }

    @Override
    public RankRewardDO get(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, RankRewardDO.class);
    }

    @Override
    public List<RankRewardDO> list(RankType rankType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(rankType));
        return  mongoTemplate.find(query, RankRewardDO.class);
    }
}
