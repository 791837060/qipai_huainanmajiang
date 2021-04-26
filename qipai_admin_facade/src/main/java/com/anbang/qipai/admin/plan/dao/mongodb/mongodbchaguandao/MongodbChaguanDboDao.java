package com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanDbo;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanDboDao;
import com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao.repository.ChaguanDboRepository;
import org.springframework.util.StringUtils;

@Component
public class MongodbChaguanDboDao implements ChaguanDboDao {

	@Override
	public void removeById(String chaguanId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(chaguanId));
		mongoTemplate.remove(query,ChaguanDbo.class);
	}

	@Autowired
	private ChaguanDboRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(ChaguanDbo dbo) {
		repository.save(dbo);
	}

	@Override
	public ChaguanDbo findByChaguanId(String chaguanId) {
		return repository.findOne(chaguanId);
	}

	@Override
	public long count(String agentId) {
		Query query = new Query();
		if(!StringUtils.isEmpty(agentId)) {
			query.addCriteria(Criteria.where("agentId").is(agentId));
		}
		return mongoTemplate.count(query,ChaguanDbo.class);
	}

	@Override
	public List<ChaguanDbo> find(int page, int size,String agentId) {
		Query query = new Query();
		if(!StringUtils.isEmpty(agentId)) {
			query.addCriteria(Criteria.where("agentId").is(agentId));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));
		return mongoTemplate.find(query, ChaguanDbo.class);
	}

}
