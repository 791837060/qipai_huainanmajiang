package com.anbang.qipai.admin.plan.dao.mongodb.mongodbgamedao;

import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.dao.gamedao.GameHistoricalPanResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbGameHistoricalPanResultDao implements GameHistoricalPanResultDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addGameHistoricalResult(GameHistoricalPanResult result) {
		mongoTemplate.insert(result);
	}

	@Override
	public List<GameHistoricalPanResult> findGameHistoricalResultByGameIdAndGame(int page, int size, String gameId,
			Game game) {
		Query query = new Query();
		query.addCriteria(Criteria.where("gameId").is(gameId));
		query.addCriteria(Criteria.where("game").is(game));
		query.limit(size);
		query.skip((page - 1) * size);
		query.with(new Sort(new Order(Direction.ASC, "no")));
		return mongoTemplate.find(query, GameHistoricalPanResult.class);
	}

	@Override
	public long getAmountByGameIdAndGame(String gameId, Game game) {
		Query query = new Query();
		query.addCriteria(Criteria.where("gameId").is(gameId));
		query.addCriteria(Criteria.where("game").is(game));
		return mongoTemplate.count(query, GameHistoricalPanResult.class);
	}

	@Override
	public List<GameHistoricalPanResult> findPanResultByGameId(String gameId){
		Query query = new Query();
		query.addCriteria(Criteria.where("gameId").is(gameId));
		query.with(new Sort(new Order(Direction.ASC, "no")));
		return mongoTemplate.find(query, GameHistoricalPanResult.class);
	}

}
