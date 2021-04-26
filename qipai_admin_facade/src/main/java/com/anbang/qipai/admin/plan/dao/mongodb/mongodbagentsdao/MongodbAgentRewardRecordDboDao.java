package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.agents.AgentRewardType;
import com.mongodb.BasicDBObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.agents.AgentRewardRecordDbo;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentRewardRecordDboDao;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentRewardRecordDboVO;

@Component
public class MongodbAgentRewardRecordDboDao implements AgentRewardRecordDboDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addAgentRewardRecordDbo(AgentRewardRecordDbo record) {
		mongoTemplate.insert(record);
	}

	@Override
	public long getAmountByConditions(AgentRewardRecordDboVO record) {
		Query query = new Query();
		if (record.getAgentId() != null && !"".equals(record.getAgentId())) {
			query.addCriteria(Criteria.where("agentId").is(record.getAgentId()));
		}
		if (record.getBossId() != null && !"".equals(record.getBossId())) {
			query.addCriteria(Criteria.where("bossId").is(record.getBossId()));
		}
		if (record.getMemberId() != null && !"".equals(record.getMemberId())) {
			query.addCriteria(Criteria.where("memberId").is(record.getMemberId()));
		}
		if (StringUtils.isNotBlank(record.getRewardType())) {
			query.addCriteria(Criteria.where("summary.text").is(AgentRewardType.rewardTypeMap.get(record.getRewardType())));
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
		return mongoTemplate.count(query, AgentRewardRecordDbo.class);
	}

	@Override
	public List<AgentRewardRecordDbo> findAgentRewardRecordDboByConditions(int page, int size,
			AgentRewardRecordDboVO record) {
		Query query = new Query();
		if (record.getAgentId() != null && !"".equals(record.getAgentId())) {
			query.addCriteria(Criteria.where("agentId").is(record.getAgentId()));
		}
		if (record.getBossId() != null && !"".equals(record.getBossId())) {
			query.addCriteria(Criteria.where("bossId").is(record.getBossId()));
		}
		if (record.getMemberId() != null && !"".equals(record.getMemberId())) {
			query.addCriteria(Criteria.where("memberId").is(record.getMemberId()));
		}
		if (StringUtils.isNotBlank(record.getRewardType())) {
			query.addCriteria(Criteria.where("summary.text").is(AgentRewardType.rewardTypeMap.get(record.getRewardType())));
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
		query.limit(size);
		query.skip((page - 1) * size);
		return mongoTemplate.find(query, AgentRewardRecordDbo.class);
	}

	@Override
	public double findAmountByConditions(AgentRewardRecordDboVO record) {
		Criteria criteria = new Criteria();
		if (record.getAgentId() != null && !"".equals(record.getAgentId())) {
			criteria = criteria.and("agentId").is(record.getAgentId());
		}
		if (record.getBossId() != null && !"".equals(record.getBossId())) {
			criteria = criteria.and("bossId").is(record.getBossId());
		}
		if (StringUtils.isNotBlank(record.getRewardType())) {
			criteria = criteria.and("summary.text").is(AgentRewardType.rewardTypeMap.get(record.getRewardType()));
		}
		if (record.getStartTime() != null || record.getEndTime() != null) {
			criteria = criteria.and("accountingTime");
			if (record.getStartTime() != null) {
				criteria = criteria.gte(record.getStartTime());
			}
			if (record.getEndTime() != null) {
				criteria = criteria.lte(record.getEndTime());
			}
		}
		Aggregation aggregation = Aggregation.newAggregation(AgentRewardRecordDbo.class,
				Aggregation.match(criteria),
				Aggregation.group().sum("accountingAmount").as("totalReward"));
		AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(aggregation, AgentRewardRecordDbo.class, BasicDBObject.class);
		List<BasicDBObject> list = result.getMappedResults();
		if (list.isEmpty()) {
			return 0;
		}
		BasicDBObject basicObj = list.get(0);
		return basicObj.getDouble("totalReward");
	}

}
