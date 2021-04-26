package com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanMemberDbo;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanMemberDboDao;
import com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao.repository.ChaguanMemberDboRepository;

@Component
public class MongodbChaguanMemberDboDao implements ChaguanMemberDboDao {

	@Autowired
	private ChaguanMemberDboRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(ChaguanMemberDbo dbo) {
		repository.save(dbo);
	}

	@Override
	public List<ChaguanMemberDbo> findByChaguanId(int page, int size, String chaguanId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("chaguanId").is(chaguanId));
		query.addCriteria(Criteria.where("remove").is(false));
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, ChaguanMemberDbo.class);
	}

	@Override
	public long countByChaguanId(String chaguanId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("chaguanId").is(chaguanId));
		query.addCriteria(Criteria.where("remove").is(false));
		return mongoTemplate.count(query, ChaguanMemberDbo.class);
	}

	@Override
	public void removeByChaguanId(String chaguanId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("chaguanId").is(chaguanId));
		mongoTemplate.remove(query,ChaguanMemberDbo.class);
	}

}
