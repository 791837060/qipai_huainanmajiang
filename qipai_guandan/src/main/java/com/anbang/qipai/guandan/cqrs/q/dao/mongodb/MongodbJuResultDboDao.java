package com.anbang.qipai.guandan.cqrs.q.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.guandan.cqrs.q.dao.JuResultDboDao;
import com.anbang.qipai.guandan.cqrs.q.dao.mongodb.repository.JuResultDboRepository;
import com.anbang.qipai.guandan.cqrs.q.dbo.JuResultDbo;

@Component
public class MongodbJuResultDboDao implements JuResultDboDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private JuResultDboRepository repository;

	@Override
	public void save(JuResultDbo juResultDbo) {
		repository.save(juResultDbo);
	}

	@Override
	public JuResultDbo findByGameId(String gameId) {
		return repository.findOneByGameId(gameId);
	}

	@Override
	public void removeByTime(long endTime) {
		mongoTemplate.remove(new Query(Criteria.where("finishTime").lt(endTime)), JuResultDbo.class);
	}

}
