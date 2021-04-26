package com.anbang.qipai.dalianmeng.plan.dao.mongodb;

import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameServer;
import com.anbang.qipai.dalianmeng.plan.dao.GameServerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

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
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));
        return this.mongoTemplate.find(query, GameServer.class);
    }

    @Override
    public void updateGameServerState(List<String> ids, int state) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));
        Update update = new Update();
        update.set("state", state);
        this.mongoTemplate.updateMulti(query, update, GameServer.class);
    }

    @Override
    public List<GameServer> findServersByState(Game game, int state) {
        Query query = new Query();
        query.addCriteria(Criteria.where("game").is(game).orOperator(new Criteria("state").is(state),
                new Criteria("state").exists(false)));
        return this.mongoTemplate.find(query, GameServer.class);
    }

}
