package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.agents.AgentImageDbo;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentImageDboDao;

@Component
public class MongodbAgentImageDboDao implements AgentImageDboDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addAgentImageDbo(AgentImageDbo image) {
		mongoTemplate.insert(image);
	}

	@Override
	public List<AgentImageDbo> findAgentImageDbo() {
		Query query = new Query();
		query.with(new Sort(new Order(Direction.ASC, "ordinal")));
		return mongoTemplate.find(query, AgentImageDbo.class);
	}

	@Override
	public void deleteAgentImageDboById(String imageId) {
		Query query = new Query(Criteria.where("id").is(imageId));
		mongoTemplate.remove(query, AgentImageDbo.class);
	}

	@Override
	public AgentImageDbo findAgentImageDboById(String imageId) {
		Query query = new Query(Criteria.where("id").is(imageId));
		return mongoTemplate.findOne(query, AgentImageDbo.class);
	}

	@Override
	public void deleteAgentImageDboByOrdinal(int ordinal) {
		Query query = new Query(Criteria.where("ordinal").is(ordinal));
		mongoTemplate.remove(query, AgentImageDbo.class);
	}

}
