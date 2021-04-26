package com.anbang.qipai.dalianmeng.cqrs.q.dao.mongodb;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.AllianceDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.AllianceDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class MongodbAllianceDboDao implements AllianceDboDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(AllianceDbo allianceDbo) {
        mongoTemplate.insert(allianceDbo);
    }

    @Override
    public AllianceDbo findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, AllianceDbo.class);
    }

    @Override
    public long countAll() {
        Query query = new Query();
        return mongoTemplate.count(query, AllianceDbo.class);
    }

    @Override
    public List<AllianceDbo> findByLianmengId(String lianmengId,String memberId) {
        Query query = new Query();
        if(!StringUtils.isEmpty(lianmengId)){
            query.addCriteria(Criteria.where("id").is(lianmengId));
        }
        if(!StringUtils.isEmpty(memberId)){
            query.addCriteria(Criteria.where("mengzhu").is(memberId));
        }
        return mongoTemplate.find(query, AllianceDbo.class);
    }

    @Override
    public long countByAgentIdAndLianmengId(String agentId, String lianmengId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(agentId)) {
            query.addCriteria(Criteria.where("agentId").is(agentId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("id").is(lianmengId));
        }
        return mongoTemplate.count(query, AllianceDbo.class);
    }

    @Override
    public List<AllianceDbo> findByAgentIdAndLianmengId(String agentId, String lianmengId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(agentId)) {
            query.addCriteria(Criteria.where("agentId").is(agentId));
        }
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("id").is(lianmengId));
        }
        return mongoTemplate.find(query, AllianceDbo.class);
    }

    @Override
    public List<AllianceDbo> findByMengzhuAndPage(int page, int size, String mengzhu) {
        Query query = new Query();
        if (!StringUtils.isEmpty(mengzhu)) {
            query.addCriteria(Criteria.where("mengzhu").is(mengzhu));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, AllianceDbo.class);
    }

    @Override
    public List<AllianceDbo> findByMengzhu(String mengzhu) {
        Query query = new Query();
        if (!StringUtils.isEmpty(mengzhu)) {
            query.addCriteria(Criteria.where("mengzhu").is(mengzhu));
        }
        return mongoTemplate.find(query, AllianceDbo.class);
    }

    @Override
    public void removeById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, AllianceDbo.class);
    }

    @Override
    public long countByConditions(String agentId, String nickname, String phone, String userName) {
        Query query = new Query();
        if (!StringUtils.isEmpty(agentId)) {
            query.addCriteria(Criteria.where("agentId").is(agentId));
        }
        if (!StringUtils.isEmpty(nickname)) {
            query.addCriteria(Criteria.where("nickname").is(nickname));
        }
        if (!StringUtils.isEmpty(phone)) {
            query.addCriteria(Criteria.where("phone").is(phone));
        }
        if (!StringUtils.isEmpty(userName)) {
            query.addCriteria(Criteria.where("userName").is(userName));
        }
        return mongoTemplate.count(query, AllianceDbo.class);
    }

    @Override
    public List<AllianceDbo> findByConditions(int page, int size, String agentId, String nickname, String phone, String userName) {
        Query query = new Query();
        if (!StringUtils.isEmpty(agentId)) {
            query.addCriteria(Criteria.where("agentId").is(agentId));
        }
        if (!StringUtils.isEmpty(nickname)) {
            query.addCriteria(Criteria.where("nickname").is(nickname));
        }
        if (!StringUtils.isEmpty(phone)) {
            query.addCriteria(Criteria.where("phone").is(phone));
        }
        if (!StringUtils.isEmpty(userName)) {
            query.addCriteria(Criteria.where("userName").is(userName));
        }
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, AllianceDbo.class);
    }

    @Override
    public long countAmountByAgentId(String agentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("agentId").is(agentId));
        return mongoTemplate.count(query, AllianceDbo.class);
    }

    @Override
    public List<AllianceDbo> findByAgentId(int page, int size, String agentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("agentId").is(agentId));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, AllianceDbo.class);
    }

    @Override
    public void updateDesc(String lianmengId, String desc) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(lianmengId));
        Update update = new Update();
        update.set("desc", desc);
        mongoTemplate.updateFirst(query, update, AllianceDbo.class);
    }

    @Override
    public void updateSetting(String lianmengId, boolean renshuHide, boolean kongzhuoqianzhi,
                              boolean nicknameHide, boolean idHide, boolean banAlliance, boolean zhuomanHide,
                              int buzhunbeituichushichang, boolean zidongzhunbei,  boolean lianmengIdHide) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(lianmengId));
        Update update = new Update();
        update.set("renshuHide", renshuHide);
        update.set("kongzhuoqianzhi", kongzhuoqianzhi);
        update.set("nicknameHide", nicknameHide);
        update.set("idHide", idHide);
        update.set("banAlliance", banAlliance);
        update.set("zhuomanHide", zhuomanHide);
        update.set("buzhunbeituichushichang", buzhunbeituichushichang);
        update.set("zidongzhunbei", zidongzhunbei);
        update.set("lianmengIdHide", lianmengIdHide);
        mongoTemplate.updateFirst(query, update, AllianceDbo.class);
    }


}
