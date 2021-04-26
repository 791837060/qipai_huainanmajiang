package com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanGameTable;
import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanGameTableDao;
import com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao.repository.ChaguanGameTableRepository;

@Component
public class MongodbChaguanGameTableDao implements ChaguanGameTableDao {

	@Autowired
	private ChaguanGameTableRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(ChaguanGameTable gameTable) {
		repository.save(gameTable);
	}

	@Override
	public ChaguanGameTable findTableByGameAndServerGameGameId(Game game, String serverGameId) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("game").is(game).andOperator(Criteria.where("serverGame.gameId").is(serverGameId)));
		return mongoTemplate.findOne(query, ChaguanGameTable.class);
	}

	@Override
	public void updateGameTable(ChaguanGameTable gameTable) {
		repository.save(gameTable);
	}

	@Override
	public int countTableByPlayerIdAndTableNo(String playerId, String tableNo) {
		Query query = new Query();
		if (StringUtils.isNotBlank(playerId)) {
			query.addCriteria(Criteria.where("playerList").is(playerId));
		}
		if (StringUtils.isNotBlank(tableNo)) {
			query.addCriteria(Criteria.where("no").is(tableNo));
		}
		return (int) mongoTemplate.count(query, ChaguanGameTable.class);
	}

	@Override
	public List<ChaguanGameTable> findTableByPlayerIdAndTableNo(int page, int size, String playerId, String tableNo) {
		Query query = new Query();
		if (StringUtils.isNotBlank(playerId)) {
			query.addCriteria(Criteria.where("playerList").is(playerId));
		}
		if (StringUtils.isNotBlank(tableNo)) {
			query.addCriteria(Criteria.where("no").is(tableNo));
		}
		query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, ChaguanGameTable.class);
	}

}
