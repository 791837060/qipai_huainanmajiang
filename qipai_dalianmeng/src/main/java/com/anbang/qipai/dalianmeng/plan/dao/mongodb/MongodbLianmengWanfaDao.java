package com.anbang.qipai.dalianmeng.plan.dao.mongodb;

import com.anbang.qipai.dalianmeng.plan.bean.LianmengWanfa;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.dao.LianmengWanfaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbLianmengWanfaDao implements LianmengWanfaDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(LianmengWanfa wanfa) {
        mongoTemplate.insert(wanfa);
    }

    @Override
    public List<LianmengWanfa> findByLianmengId(String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.find(query, LianmengWanfa.class);
    }

    @Override
    public LianmengWanfa findByLianmengIdAndGame(String lianmengId, Game game) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.addCriteria(Criteria.where("game").is(game));
        return mongoTemplate.findOne(query, LianmengWanfa.class);
    }

    @Override
    public LianmengWanfa findByWanfaId(String wanfaId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(wanfaId));
        return mongoTemplate.findOne(query, LianmengWanfa.class);
    }

    @Override
    public void deleteLianmengWanfa(String wanfaId) {
        Query query = new Query(Criteria.where("id").in(wanfaId));
        mongoTemplate.remove(query, LianmengWanfa.class);
    }

    @Override
    public void updateLianmengWanfa(LianmengWanfa lianmengWanfa) {
        Query query = new Query(Criteria.where("id").is(lianmengWanfa.getId()));
        Update update = new Update();
        update.set("laws", lianmengWanfa.getLaws());
        update.set("contribution", lianmengWanfa.getContribution());
        update.set("minPower", lianmengWanfa.getMinPower());
        update.set("difen", lianmengWanfa.getDifen());
        update.set("wanfaName", lianmengWanfa.getWanfaName());
        update.set("payType", lianmengWanfa.getPayType());
        update.set("aaScore", lianmengWanfa.getAaScore());
        update.set("powerLimit", lianmengWanfa.getPowerLimit());
        update.set("mengzhuAADifen", lianmengWanfa.getMengzhuAADifen());
        update.set("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        update.set("lixianshichang", lianmengWanfa.getLixianshichang());
        update.set("yuanzifen", lianmengWanfa.getYuanzifen());
        update.set("zidongkaishi", lianmengWanfa.getZidongkaishi());
        update.set("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime());
        mongoTemplate.updateFirst(query, update, LianmengWanfa.class);
    }

    @Override
    public List<LianmengWanfa> findAllWafabyLianmengId(int page, int size, String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, LianmengWanfa.class);
    }
}
