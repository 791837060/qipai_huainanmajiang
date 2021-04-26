package com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanApply;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanApplyDao;
import com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao.repository.ChaguanApplyReppository;

@Component
public class MongodbChaguanApplyDao implements ChaguanApplyDao {

	@Autowired
	private ChaguanApplyReppository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(ChaguanApply apply) {
		repository.save(apply);
	}

	@Override
	public long countByConditions(ChaguanApply apply) {
		Query query = new Query();
		if (apply.getAgentId() != null) {
			query.addCriteria(Criteria.where("agentId").is(apply.getAgentId()));
		}
		if (apply.getNickname() != null) {
			query.addCriteria(Criteria.where("nickname").is(apply.getNickname()));
		}
		query.addCriteria(Criteria.where("status").is(apply.getStatus()));
		return mongoTemplate.count(query, ChaguanApply.class);
	}

	@Override
	public List<ChaguanApply> findByConditions(int page, int size, ChaguanApply apply) {
		Query query = new Query();
		if (apply.getAgentId() != null) {
			query.addCriteria(Criteria.where("agentId").is(apply.getAgentId()));
		}
		if (apply.getNickname() != null) {
			query.addCriteria(Criteria.where("nickname").is(apply.getNickname()));
		}
		query.addCriteria(Criteria.where("status").is(apply.getStatus()));
		query.skip((page - 1) * size);
		query.limit(size);
		query.with(new Sort(new Order(Direction.ASC, "createTime")));
		return mongoTemplate.find(query, ChaguanApply.class);
	}

}
