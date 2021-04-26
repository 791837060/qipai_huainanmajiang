package com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.PlayBackDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb.repository.PlayBackDboRepository;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.PlayBackDbo;
import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MongodbPlayBackDboDao implements PlayBackDboDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private PlayBackDboRepository playBackDboRepository;

	@Override
	public void save(PlayBackDbo dbo) {
		playBackDboRepository.save(dbo);
	}

	@Override
	public PlayBackDbo findById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, PlayBackDbo.class);
	}

	@Override
	public PlayBackDbo findByGameAndGameIdAndPanNo(Game game, String gameId, int panNo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("game").is(game));
		query.addCriteria(Criteria.where("gameId").is(gameId));
		query.addCriteria(Criteria.where("panNo").is(panNo));
		return mongoTemplate.findOne(query, PlayBackDbo.class);
	}

}
