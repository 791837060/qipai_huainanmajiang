package com.anbang.qipai.admin.plan.dao.mongodb.mongotaskdao;

import com.anbang.qipai.admin.plan.bean.tasks.HongbaodianProduct;
import com.anbang.qipai.admin.plan.dao.tasksdao.ExchangeManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yins
 * @Description: TODO
 */
@Component
public class MongodbExchangeManageDao implements ExchangeManageDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addExchangeManage(HongbaodianProduct hongbaodianProduct) {
        mongoTemplate.insert(hongbaodianProduct);
    }

    @Override
    public void updateExchangeManage(HongbaodianProduct hongbaodianProduct) {
        Query query = new Query(Criteria.where("id").is(hongbaodianProduct.getId()));
        Update update = new Update();
        update.set("name", hongbaodianProduct.getName());
        update.set("price", hongbaodianProduct.getPrice());
        update.set("rewardNum", hongbaodianProduct.getRewardNum());
        update.set("rewardType", hongbaodianProduct.getRewardType());
        mongoTemplate.updateFirst(query, update, HongbaodianProduct.class);
    }

    @Override
    public void deleteExchangeManage(String[] ids) {
        Query query = new Query(Criteria.where("id").in(ids));
        mongoTemplate.remove(query, HongbaodianProduct.class);
    }

    @Override
    public HongbaodianProduct getExchangeManage(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, HongbaodianProduct.class);
    }

    @Override
    public long countExchangeMange() {
        Query query = new Query();
        return mongoTemplate.count(query, HongbaodianProduct.class);
    }

    @Override
    public List<HongbaodianProduct> getExchangeManages(int page, int size) {
        Query query = new Query();
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, HongbaodianProduct.class);
    }
}
