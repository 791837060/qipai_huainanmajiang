package com.anbang.qipai.admin.plan.dao.mongodb.mongodbjuprizedao;

import com.anbang.qipai.admin.plan.bean.games.GameVersion;
import com.anbang.qipai.admin.plan.bean.juprize.JuPrize;
import com.anbang.qipai.admin.plan.dao.juprizedao.JuPrizeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 */
@Component
public class MongodbJuPrizeDao implements JuPrizeDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(JuPrize juPrize) {
        mongoTemplate.save(juPrize);
    }

    @Override
    public void delete(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, JuPrize.class);
    }

    @Override
    public JuPrize getJuPrize(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, JuPrize.class);
    }

    @Override
    public List<JuPrize> listJuPrize() {
        return mongoTemplate.findAll(JuPrize.class);
    }

    @Override
    public void updateStoreNum(String id, int storeNum) {
        mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)),
                new Update().set("storeNum", storeNum), JuPrize.class);
    }
}
