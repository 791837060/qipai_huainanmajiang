package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.agents.AgentRewardOrder;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentRewardOrderDao;

@Component
public class MongodbAgentRewardOrderDao implements AgentRewardOrderDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(AgentRewardOrder order) {
		mongoTemplate.insert(order);
	}

	@Override
	public AgentRewardOrder findById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, AgentRewardOrder.class);
	}

	@Override
	public void updateReturnParams(String id, Map returnParams) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("returnParams", returnParams);
		mongoTemplate.updateFirst(query, update, AgentRewardOrder.class);
	}

}
