package com.anbang.qipai.admin.plan.dao.mongodb.mongoshopdao;

import com.anbang.qipai.admin.plan.bean.shop.GoodsType;
import com.anbang.qipai.admin.plan.bean.shop.ScoreShopProductDbo;
import com.anbang.qipai.admin.plan.dao.shopdao.ScoreShopProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbScoreShopProductDao implements ScoreShopProductDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(ScoreShopProductDbo scoreShopProductDbo) {
        mongoTemplate.save(scoreShopProductDbo);
    }

    @Override
    public void delete(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, ScoreShopProductDbo.class);
    }

    @Override
    public List<ScoreShopProductDbo> listScoreShopProductDbo(int page, int size) {
        Query query = new Query();
//        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, ScoreShopProductDbo.class);
    }

    @Override
    public List<ScoreShopProductDbo> listScoreShopProductDbo() {
        Query query = new Query();
        return mongoTemplate.find(query, ScoreShopProductDbo.class);
    }

    @Override
    public long countScoreShopProductDbo() {
        Query query = new Query();
        return mongoTemplate.count(query, ScoreShopProductDbo.class);
    }


    @Override
    public ScoreShopProductDbo getScoreShopProductDbo(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, ScoreShopProductDbo.class);
    }

    @Override
    public void updateSpentNum(String id, int spentNum) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("spentNum", spentNum);
        mongoTemplate.updateFirst(query, update, ScoreShopProductDbo.class);
    }
}
