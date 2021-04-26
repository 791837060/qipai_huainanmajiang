package com.anbang.qipai.admin.plan.dao.mongodb.mongodbgamedao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.games.GameLaw;
import com.anbang.qipai.admin.plan.dao.gamedao.GameLawDao;

@Component
public class MongodbGameLawDao implements GameLawDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(GameLaw law) {
		mongoTemplate.insert(law);
	}

	@Override
	public void remove(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, GameLaw.class);
	}

	@Override
	public List<GameLaw> findGameLawByConditions(int page, int size, GameLaw law) {
		Query query = new Query();
		if (law.getName() != null && !"".equals(law.getName())) {
			query.addCriteria(Criteria.where("name").regex(law.getName()));
		}
		if (law.getGame() != null) {
			query.addCriteria(Criteria.where("game").is(law.getGame()));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, GameLaw.class);
	}

	@Override
	public long getAmountByConditions(GameLaw law) {
		Query query = new Query();
		if (law.getName() != null && !"".equals(law.getName())) {
			query.addCriteria(Criteria.where("name").regex(law.getName()));
		}
		if (law.getGame() != null) {
			query.addCriteria(Criteria.where("game").is(law.getGame()));
		}
		return mongoTemplate.count(query, GameLaw.class);
	}

	@Override
	public void update(GameLaw law) {
		Query query = new Query(Criteria.where("id").is(law.getId()));
		Update update = new Update();
		update.set("game", law.getGame());
		update.set("name", law.getName());
		update.set("desc", law.getDesc());
		update.set("mutexGroupId", law.getMutexGroupId());
		update.set("vip", law.isVip());
		mongoTemplate.updateFirst(query, update, GameLaw.class);
	}

}
