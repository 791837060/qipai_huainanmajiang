package com.anbang.qipai.dalianmeng.plan.dao.mongodb;

import com.anbang.qipai.dalianmeng.plan.bean.LianmengDiamondDayCost;
import com.anbang.qipai.dalianmeng.plan.dao.LianmengDiamondDayCostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbLianmengDiamondDayCostDao implements LianmengDiamondDayCostDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(LianmengDiamondDayCost diamondDayCost) {
        mongoTemplate.insert(diamondDayCost);
    }

    @Override
    public LianmengDiamondDayCost findByLianmengId(String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        if (startTime != 0 && endTime != 0) {
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        return mongoTemplate.findOne(query, LianmengDiamondDayCost.class);
    }

    @Override
    public void updateCostByLianmengId(String lianmengId, long startTime, long endTime, int cost) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        if (startTime != 0 && endTime != 0) {
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        Update update = new Update();
        update.set("cost", cost);
        mongoTemplate.updateMulti(query, update, LianmengDiamondDayCost.class);
    }

    @Override
    public void deleteByTime(long startTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").lt(startTime));
        mongoTemplate.remove(query, LianmengDiamondDayCost.class);
    }

    @Override
    public List<LianmengDiamondDayCost> findAllByLianmengId(String lianmengId, long startTime, long endTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        if (startTime != 0 && endTime != 0) {
            query.addCriteria(Criteria.where("createTime").gt(startTime).lt(endTime));
        }
        return mongoTemplate.find(query, LianmengDiamondDayCost.class);
    }
}
