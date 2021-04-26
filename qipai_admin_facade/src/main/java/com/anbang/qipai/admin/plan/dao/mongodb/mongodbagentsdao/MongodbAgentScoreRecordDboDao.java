package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.agents.AgentScoreRecordDbo;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentScoreRecordDboDao;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentScoreRecordDboVO;

@Component
public class MongodbAgentScoreRecordDboDao implements AgentScoreRecordDboDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addAgentScoreRecordDbo(AgentScoreRecordDbo record) {
		mongoTemplate.insert(record);
	}

	@Override
	public long getAmountByConditions(AgentScoreRecordDboVO record) {
		Query query = new Query();
		if (record.getAgentId() != null && !"".equals(record.getAgentId())) {
			query.addCriteria(Criteria.where("agentId").is(record.getAgentId()));
		}
		if (record.getAgent() != null && !"".equals(record.getAgent())) {
			query.addCriteria(Criteria.where("agent").regex(record.getAgent()));
		}
		if (record.getType() != null && !"".equals(record.getType())) {
			query.addCriteria(Criteria.where("summary.text").regex(record.getType()));
		}
		if (record.getStartTime() != null || record.getEndTime() != null) {
			Criteria criteria = Criteria.where("accountingTime");
			if (record.getStartTime() != null) {
				criteria = criteria.gte(record.getStartTime());
			}
			if (record.getEndTime() != null) {
				criteria = criteria.lte(record.getEndTime());
			}
			query.addCriteria(criteria);
		}
		return mongoTemplate.count(query, AgentScoreRecordDbo.class);
	}

	@Override
	public List<AgentScoreRecordDbo> findAgentScoreRecordDboByConditions(int page, int size,
			AgentScoreRecordDboVO record) {
		Query query = new Query();
		if (record.getAgentId() != null && !"".equals(record.getAgentId())) {
			query.addCriteria(Criteria.where("agentId").is(record.getAgentId()));
		}
		if (record.getAgent() != null && !"".equals(record.getAgent())) {
			query.addCriteria(Criteria.where("agent").regex(record.getAgent()));
		}
		if (record.getType() != null && !"".equals(record.getType())) {
			query.addCriteria(Criteria.where("summary.text").regex(record.getType()));
		}
		if (record.getStartTime() != null || record.getEndTime() != null) {
			Criteria criteria = Criteria.where("accountingTime");
			if (record.getStartTime() != null) {
				criteria = criteria.gte(record.getStartTime());
			}
			if (record.getEndTime() != null) {
				criteria = criteria.lte(record.getEndTime());
			}
			query.addCriteria(criteria);
		}
		query.with(record.getSort());
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, AgentScoreRecordDbo.class);
	}

}
