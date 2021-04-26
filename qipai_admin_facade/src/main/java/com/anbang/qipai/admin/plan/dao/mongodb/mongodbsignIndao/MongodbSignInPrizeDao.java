package com.anbang.qipai.admin.plan.dao.mongodb.mongodbsignIndao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.signin.SignInPrize;
import com.anbang.qipai.admin.plan.dao.signindao.SignInPrizeDao;

@Component
public class MongodbSignInPrizeDao implements SignInPrizeDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addSignInPrize(SignInPrize signInPrize) {
        mongoTemplate.insert(signInPrize);
    }

    @Override
    public void save(SignInPrize signInPrize) {
        mongoTemplate.save(signInPrize);
    }

    @Override
    public void updateSignInPrize(SignInPrize signInPrize) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(signInPrize.getId()));
        Update update = new Update();
        update.set("name", signInPrize.getName());
        update.set("type", signInPrize.getType());
        update.set("cardType", signInPrize.getCardType());
        update.set("singleNum", signInPrize.getSingleNum());
        update.set("storeNum", signInPrize.getStoreNum());
        update.set("iconUrl", signInPrize.getIconUrl());
        update.set("prizeProb", signInPrize.getPrizeProb());
        update.set("firstPrizeProb", signInPrize.getFirstPrizeProb());
        update.set("overstep", signInPrize.getOverstep());
        //update.set("state", "1");
        mongoTemplate.updateFirst(query, update, SignInPrize.class);
    }

    @Override
    public List<SignInPrize> querySignInPrize() {
        return mongoTemplate.findAll(SignInPrize.class);
    }

    @Override
    public SignInPrize querySignInPrizeById(String id) {
        return mongoTemplate.findById(id, SignInPrize.class);
    }

    @Override
    public void deleteSignInPrizeById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, SignInPrize.class);
    }

    @Override
    public long countSignInPrize() {
        Query query = new Query();
        return mongoTemplate.count(query, SignInPrize.class);
    }


    @Override
    public int findIndex() {
        Query query = new Query();
        return (int) mongoTemplate.count(query, SignInPrize.class);
    }

    @Override
    public void releaseSignInPrize() {
        //Query query = new Query();
        //Update update = new Update();
        //update.set("state", "0");
        //mongoTemplate.updateMulti(query, update, SignInPrize.class);
    }

    @Override
    public void decreaseStoreById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        SignInPrize signInPrize = mongoTemplate.findOne(query, SignInPrize.class);
        Update update = new Update();
        update.set("storeNum", signInPrize.getStoreNum() - 1);
        mongoTemplate.updateFirst(query, update, SignInPrize.class);
    }

}
