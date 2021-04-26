package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.agents.AgentType;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentTypeDao;

@Component
public class MongodbAgentTypeDao implements AgentTypeDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(AgentType type) {
		mongoTemplate.insert(type);
	}

	@Override
	public void removeByIds(String[] typeIds) {
		Object[] ids = typeIds;
		Query query = new Query(Criteria.where("id").in(ids));
		mongoTemplate.remove(query, AgentType.class);
	}

	@Override
	public void updateAgentType(AgentType type) {
		Query query = new Query(Criteria.where("id").is(type.getId()));
		Update update = new Update();
		update.set("memberReward", type.getMemberReward());
		update.set("juniorReward", type.getJuniorReward());
		update.set("type", type.getType());
		mongoTemplate.updateFirst(query, update, AgentType.class);
	}

	@Override
	public List<AgentType> findByConditions(int page, int size, AgentType type) {
		Query query = new Query();
		query.limit(size);
		query.skip((page - 1) * size);
		return mongoTemplate.find(query, AgentType.class);
	}

	@Override
	public long countAmountByConditions(AgentType type) {
		Query query = new Query();
		return mongoTemplate.count(query, AgentType.class);
	}

	@Override
	public AgentType findById(String typeId) {
		Query query = new Query(Criteria.where("id").is(typeId));
		return mongoTemplate.findOne(query, AgentType.class);
	}

	@Override
	public AgentType findByType(String type) {
		Query query = new Query(Criteria.where("type").is(type));
		return mongoTemplate.findOne(query, AgentType.class);
	}

}
