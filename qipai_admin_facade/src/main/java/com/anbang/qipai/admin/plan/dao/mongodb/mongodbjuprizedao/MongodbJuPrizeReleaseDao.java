package com.anbang.qipai.admin.plan.dao.mongodb.mongodbjuprizedao;

import com.anbang.qipai.admin.plan.bean.juprize.JuPrize;
import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeRelease;
import com.anbang.qipai.admin.plan.dao.juprizedao.JuPrizeReleaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 */
@Component
public class MongodbJuPrizeReleaseDao implements JuPrizeReleaseDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(JuPrizeRelease juPrizeRelease) {
        mongoTemplate.save(juPrizeRelease);
    }

    @Override
    public void delete(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, JuPrizeRelease.class);
    }

    @Override
    public List<JuPrizeRelease> listJuPrize() {
        return mongoTemplate.findAll(JuPrizeRelease.class);
    }
}
