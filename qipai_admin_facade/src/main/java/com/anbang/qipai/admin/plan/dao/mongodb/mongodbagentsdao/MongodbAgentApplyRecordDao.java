package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.agents.AgentApplyRecord;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentApplyRecordDao;

@Component
public class MongodbAgentApplyRecordDao implements AgentApplyRecordDao {

	@Autowired
	private MongoTemplate mongoTempalte;

	@Override
	public void addAgentApplyRecord(AgentApplyRecord record) {
		mongoTempalte.insert(record);
	}

	@Override
	public void updateAgentApplyRecordSate(String recordId, String state) {
		Query query = new Query(Criteria.where("id").is(recordId));
		Update update = new Update();
		update.set("state", state);
		mongoTempalte.updateFirst(query, update, AgentApplyRecord.class);
	}

	@Override
	public List<AgentApplyRecord> findAgentApplyRecordByConditions(int page, int size, AgentApplyRecord record) {
		Query query = new Query();
		if (record.getAgentId() != null && !"".equals(record.getAgentId())) {
			query.addCriteria(Criteria.where("agentId").is(record.getAgentId()));
		}
		if (record.getNickname() != null && !"".equals(record.getNickname())) {
			query.addCriteria(Criteria.where("nickname").regex(record.getNickname()));
		}
		query.addCriteria(Criteria.where("state").is("APPLYING"));
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTempalte.find(query, AgentApplyRecord.class);
	}

	@Override
	public long getAmountByConditions(int page, int size, AgentApplyRecord record) {
		Query query = new Query();
		if (record.getAgentId() != null && !"".equals(record.getAgentId())) {
			query.addCriteria(Criteria.where("agentId").is(record.getAgentId()));
		}
		if (record.getNickname() != null && !"".equals(record.getNickname())) {
			query.addCriteria(Criteria.where("nickname").regex(record.getNickname()));
		}
		query.addCriteria(Criteria.where("state").is("APPLYING"));
		return mongoTempalte.count(query, AgentApplyRecord.class);
	}

	@Override
	public AgentApplyRecord findAgentApplyRecordById(String recordId) {
		Query query = new Query(Criteria.where("id").is(recordId));
		return mongoTempalte.findOne(query, AgentApplyRecord.class);
	}

}
