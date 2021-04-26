package com.anbang.qipai.admin.plan.dao.mongodb.mongodbgamedao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.games.GameServer;
import com.anbang.qipai.admin.plan.dao.gamedao.GameServerDao;

@Component
public class MongodbGameServerDao implements GameServerDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(GameServer gameServer) {
		mongoTemplate.insert(gameServer);
	}

	@Override
	public void remove(String[] ids) {
		Object[] serverIds = ids;
		Query query = new Query(Criteria.where("id").in(serverIds));
		mongoTemplate.remove(query, GameServer.class);
	}

	@Override
	public List<GameServer> findAllByGame(Game game) {
		Query query = new Query();
		if (game != null) {
			query.addCriteria(Criteria.where("game").is(game));
		}
		return mongoTemplate.find(query, GameServer.class);
	}

	@Override
    public List<GameServer> findGameServersByIds(List<String> ids) {
		Query query=new Query();
		query.addCriteria(Criteria.where("id").in(ids));
        return this.mongoTemplate.find(query,GameServer.class);
	}

    @Override
    public void updateGameServerState(List<String> ids, int state) {
        Query query=new Query();
        query.addCriteria(Criteria.where("id").in(ids));
        Update update=new Update();
        update.set("state",state);
        this.mongoTemplate.updateMulti(query,update,GameServer.class);
    }

}
