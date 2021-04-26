package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import com.anbang.qipai.admin.plan.bean.agents.AgentMemberDayCost;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentMemberDayCostDao;
import com.anbang.qipai.admin.web.query.AgentMemberCostQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbAgentMemberDayCostDao implements AgentMemberDayCostDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(AgentMemberDayCost agentMemberDayCost) {
        mongoTemplate.save(agentMemberDayCost);
    }

    @Override
    public void updateDayTotalCost(String id, int dayTotalCost) {
        Update update = new Update();
        update.set("dayTotalCost", dayTotalCost);
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.updateFirst(query, update, AgentMemberDayCost.class);
    }

    @Override
    public AgentMemberDayCost get(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, AgentMemberDayCost.class);
    }

    @Override
    public int countByQuery(AgentMemberCostQuery costQuery) {
        Query query = new Query(Criteria.where("type").is("gold"));
        if (StringUtils.isNotBlank(costQuery.getAgentId())) {
            query.addCriteria(Criteria.where("agentId").is(costQuery.getAgentId()));
        }
        if (StringUtils.isNotBlank(costQuery.getNickName())) {
            query.addCriteria(Criteria.where("nickName").regex(costQuery.getNickName()));
        }
        if (costQuery.getStartTime() != null || costQuery.getEndTime() != null) {
            Criteria criteria = Criteria.where("createTime");
            if (costQuery.getStartTime() != null) {
                criteria = criteria.gte(costQuery.getStartTime());
            }
            if (costQuery.getEndTime() != null) {
                criteria = criteria.lte(costQuery.getEndTime());
            }
            query.addCriteria(criteria);
        }
        return (int) mongoTemplate.count(query, AgentMemberDayCost.class);
    }

    @Override
    public List<AgentMemberDayCost> listByQuery(AgentMemberCostQuery costQuery, int page, int size) {
        Query query = new Query(Criteria.where("type").is("gold"));
        if (StringUtils.isNotBlank(costQuery.getAgentId())) {
            query.addCriteria(Criteria.where("agentId").is(costQuery.getAgentId()));
        }
        if (StringUtils.isNotBlank(costQuery.getNickName())) {
            query.addCriteria(Criteria.where("nickName").regex(costQuery.getNickName()));
        }
        if (costQuery.getStartTime() != null || costQuery.getEndTime() != null) {
            Criteria criteria = Criteria.where("createTime");
            if (costQuery.getStartTime() != null) {
                criteria = criteria.gte(costQuery.getStartTime());
            }
            if (costQuery.getEndTime() != null) {
                criteria = criteria.lte(costQuery.getEndTime());
            }
            query.addCriteria(criteria);
        }

        query.with(costQuery.getSort());
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, AgentMemberDayCost.class);
    }




}
