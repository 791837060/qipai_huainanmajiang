package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.agents.AgentWithdrawRecordDbo;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentWithdrawRecordDboDao;

@Component
public class MongodbAgentWithdrawRecordDboDao implements AgentWithdrawRecordDboDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(AgentWithdrawRecordDbo dbo) {
		mongoTemplate.insert(dbo);
	}

	@Override
	public long countAmountByConditions(AgentWithdrawRecordDbo dbo) {
		Query query = new Query();
		if (dbo.getAgentId() != null && !"".equals(dbo.getAgentId())) {
			query.addCriteria(Criteria.where("agentId").is(dbo.getAgentId()));
		}
		if (dbo.getState() != null && !"".equals(dbo.getState())) {
			query.addCriteria(Criteria.where("state").is(dbo.getState()));
		}
		return mongoTemplate.count(query, AgentWithdrawRecordDbo.class);
	}

	@Override
	public List<AgentWithdrawRecordDbo> findByConditions(int page, int size, AgentWithdrawRecordDbo dbo) {
		Query query = new Query();
		if (dbo.getAgentId() != null && !"".equals(dbo.getAgentId())) {
			query.addCriteria(Criteria.where("agentId").is(dbo.getAgentId()));
		}
		if (dbo.getState() != null && !"".equals(dbo.getState())) {
			query.addCriteria(Criteria.where("state").is(dbo.getState()));
		}
		query.with(new Sort(new Order(Direction.DESC, "accountingTime")));
		query.limit(size);
		query.skip((page - 1) * size);
		return mongoTemplate.find(query, AgentWithdrawRecordDbo.class);
	}

	@Override
	public void updateAgentWithdrawRecordDboState(String id, String state) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("state", state);
		mongoTemplate.updateFirst(query, update, AgentWithdrawRecordDbo.class);
	}

	@Override
	public void updateAgentWithdrawRecordDboDescAndState(String id, String state, String desc) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("desc", desc);
		update.set("state", state);
		mongoTemplate.updateFirst(query, update, AgentWithdrawRecordDbo.class);
	}

	@Override
	public AgentWithdrawRecordDbo findById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, AgentWithdrawRecordDbo.class);
	}

}
