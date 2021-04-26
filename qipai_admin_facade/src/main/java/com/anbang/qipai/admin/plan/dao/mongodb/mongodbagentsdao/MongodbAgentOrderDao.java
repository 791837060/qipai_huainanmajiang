package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import com.anbang.qipai.admin.util.TimeUtil;
import com.anbang.qipai.admin.web.query.AgentOrderQuery;
import com.mongodb.BasicDBObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.agents.AgentOrder;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentOrderDao;

import java.util.List;

@Component
public class MongodbAgentOrderDao implements AgentOrderDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addOrder(AgentOrder order) {
		mongoTemplate.insert(order);
	}

	@Override
	public void orderFinished(String id, String transaction_id, String status, long deliveTime) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("transaction_id", transaction_id);
		update.set("status", status);
		update.set("deliveTime", deliveTime);
		update.set("orderMonth", TimeUtil.getNowMonth(deliveTime,""));
		mongoTemplate.updateFirst(query, update, AgentOrder.class);
	}

	@Override
	public double findAmountByBean(AgentOrderQuery agentOrder) {
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(agentOrder.getPayerId())) {
			criteria.and("payerId").is(agentOrder.getPayerId());
		}
		if (StringUtils.isNotBlank(agentOrder.getPay_type())) {
			criteria.and("pay_type").is(agentOrder.getPay_type());
		}
		if (StringUtils.isNotBlank(agentOrder.getStatus())) {
			criteria.and("status").is(agentOrder.getStatus());
		}
		if (agentOrder.getStartTime() != null || agentOrder.getEndTime() != null) {
			criteria = criteria.and("deliveTime");
			if (agentOrder.getStartTime() != null) {
				criteria = criteria.gte(agentOrder.getStartTime());
			}
			if (agentOrder.getEndTime() != null) {
				criteria = criteria.lte(agentOrder.getEndTime());
			}
		}
		Aggregation aggregation = Aggregation.newAggregation(AgentOrder.class, Aggregation.match(criteria),
				Aggregation.group().sum("totalamount").as("total"));
		AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(aggregation, AgentOrder.class, BasicDBObject.class);
		List<BasicDBObject> list = result.getMappedResults();
		if (list.isEmpty()) {
			return 0;
		}
		BasicDBObject basicObj = list.get(0);
		return basicObj.getDouble("total");
	}

	@Override
	public long countByBean(AgentOrderQuery agentOrder) {
		Query query = new Query();
		if (StringUtils.isNotBlank(agentOrder.getPayerId())) {
			query.addCriteria(Criteria.where("payerId").is(agentOrder.getPayerId()));
		}
		if (StringUtils.isNotBlank(agentOrder.getPay_type())) {
			query.addCriteria(Criteria.where("pay_type").is(agentOrder.getPay_type()));
		}
		if (StringUtils.isNotBlank(agentOrder.getStatus())) {
			query.addCriteria(Criteria.where("status").is(agentOrder.getStatus()));
		}
		if (agentOrder.getStartTime() != null || agentOrder.getEndTime() != null) {
			Criteria criteria = Criteria.where("deliveTime");
			if (agentOrder.getStartTime() != null) {
				criteria = criteria.gte(agentOrder.getStartTime());
			}
			if (agentOrder.getEndTime() != null) {
				criteria = criteria.lte(agentOrder.getEndTime());
			}
			query.addCriteria(criteria);
		}
		query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "deliveTime")));
		return mongoTemplate.count(query, AgentOrder.class);
	}

	@Override
	public List<AgentOrder> findAgentOrderByBean(int page, int size, AgentOrderQuery agentOrder) {
		Query query = new Query();
		if (StringUtils.isNotBlank(agentOrder.getPayerId())) {
			query.addCriteria(Criteria.where("payerId").is(agentOrder.getPayerId()));
		}
		if (StringUtils.isNotBlank(agentOrder.getPay_type())) {
			query.addCriteria(Criteria.where("pay_type").is(agentOrder.getPay_type()));
		}
		if (StringUtils.isNotBlank(agentOrder.getStatus())) {
			query.addCriteria(Criteria.where("status").is(agentOrder.getStatus()));
		}
		if (agentOrder.getStartTime() != null || agentOrder.getEndTime() != null) {
			Criteria criteria = Criteria.where("deliveTime");
			if (agentOrder.getStartTime() != null) {
				criteria = criteria.gte(agentOrder.getStartTime());
			}
			if (agentOrder.getEndTime() != null) {
				criteria = criteria.lte(agentOrder.getEndTime());
			}
			query.addCriteria(criteria);
		}
		query.with(agentOrder.getSort());
		query.limit(size);
		query.skip((page - 1) * size);
		return mongoTemplate.find(query, AgentOrder.class);
	}

	@Override
	public double sumField(AgentOrderQuery agentOrder, String field) {
		Criteria criteria = new Criteria();
		if (StringUtils.isNotBlank(agentOrder.getPayerId())) {
			criteria.and("payerId").is(agentOrder.getPayerId());
		}
		if (StringUtils.isNotBlank(agentOrder.getPay_type())) {
			criteria.and("pay_type").is(agentOrder.getPay_type());
		}
		if (StringUtils.isNotBlank(agentOrder.getStatus())) {
			criteria.and("status").is(agentOrder.getStatus());
		}
		if (StringUtils.isNotBlank(agentOrder.getProductName())) {
			criteria.and("productName").is(agentOrder.getProductName());
		}
		if (agentOrder.getOrderMonth() != null) {
			criteria.and("orderMonth").is(agentOrder.getOrderMonth());
		}

		Aggregation aggregation = Aggregation.newAggregation(AgentOrder.class, Aggregation.match(criteria),
				Aggregation.group().sum(field).as("total"));
		AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(aggregation, AgentOrder.class, BasicDBObject.class);
		List<BasicDBObject> list = result.getMappedResults();
		if (list.isEmpty()) {
			return 0;
		}
		BasicDBObject basicObj = list.get(0);
		return basicObj.getDouble("total");
	}

}
