package com.anbang.qipai.admin.plan.dao.mongodb.mongoshopdao;

import com.anbang.qipai.admin.plan.bean.shop.GoodsType;
import com.anbang.qipai.admin.plan.dao.shopdao.GoodsTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbGoodsTypeDao implements GoodsTypeDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(GoodsType goodsType) {
        mongoTemplate.save(goodsType);
    }

    @Override
    public void delete(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,GoodsType.class);
    }

    @Override
    public List<GoodsType> listGoodsType() {
        return mongoTemplate.findAll(GoodsType.class);
    }

    @Override
    public GoodsType getGoodsType(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, GoodsType.class);
    }
}
