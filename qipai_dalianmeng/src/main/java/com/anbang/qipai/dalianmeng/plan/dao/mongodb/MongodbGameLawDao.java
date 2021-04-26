package com.anbang.qipai.dalianmeng.plan.dao.mongodb;


import com.anbang.qipai.dalianmeng.cqrs.q.dbo.Identity;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameLaw;
import com.anbang.qipai.dalianmeng.plan.dao.GameLawDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

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

	@Override
	public GameLaw findByGameAndName(Game game, String name) {
		Query query = new Query(Criteria.where("game").is(game));
		query.addCriteria(Criteria.where("name").is(name));
		return mongoTemplate.findOne(query, GameLaw.class);
	}


}
