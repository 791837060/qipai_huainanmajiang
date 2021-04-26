package com.anbang.qipai.qinyouquan.plan.dao.mongodb;

import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameMemberTable;
import com.anbang.qipai.qinyouquan.plan.dao.GameMemberTableDao;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbGameMemberTableDao implements GameMemberTableDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(GameMemberTable gameMemberTable) {
        mongoTemplate.insert(gameMemberTable);
    }

    @Override
    public GameMemberTable findByMemberIdAndGameTableId(String memberId, String gameTableId) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("memberId").is(memberId),
                Criteria.where("gameTable.id").is(new ObjectId(gameTableId)));
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, GameMemberTable.class);
    }

    @Override
    public long countMemberByGameAndServerGameId(Game game, String serverGameId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("gameTable.game").is(game));
        query.addCriteria(Criteria.where("gameTable.serverGame.gameId").is(serverGameId));
        return mongoTemplate.count(query, GameMemberTable.class);
    }

    @Override
    public List<GameMemberTable> findMemberGameTableByGameAndServerGameId(Game game, String serverGameId) {
        Query query = new Query(Criteria.where("gameTable.game").is(game));
        query.addCriteria(Criteria.where("gameTable.serverGame.gameId").is(serverGameId));
        return mongoTemplate.find(query, GameMemberTable.class);
    }

    @Override
    public void remove(Game game, String serverGameId, String memberId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("gameTable.game").is(game));
        query.addCriteria(Criteria.where("gameTable.serverGame.gameId").is(serverGameId));
        mongoTemplate.remove(query, GameMemberTable.class);
    }

    @Override
    public void removeExpireRoom(Game game, String serverGameId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("gameTable.game").is(game));
        query.addCriteria(Criteria.where("gameTable.serverGame.gameId").is(serverGameId));
        mongoTemplate.remove(query, GameMemberTable.class);
    }

    @Override
    public void updateMemberGameTableCurrentPanNum(Game game, String serverGameId, List<String> playerIds, int no) {
        Query query = new Query(Criteria.where("memberId").in(playerIds));
        query.addCriteria(Criteria.where("gameTable.game").is(game)
                .andOperator(Criteria.where("gameTable.serverGame.gameId").is(serverGameId)));
        Update update = new Update();
        update.set("gameTable.currentPanNum", no);
        mongoTemplate.updateMulti(query, update, GameMemberTable.class);
    }
    @Override
    public void deleteGameTableByNo(String roomNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("gameTable.no").is(roomNo));
        mongoTemplate.remove(query, GameMemberTable.class);
    }

    @Override
    public GameMemberTable findByMemberId(String memberId) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("memberId").is(memberId));
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, GameMemberTable.class);
    }

    @Override
    public GameMemberTable findByMemberIdAndLianmengId(String memberId, String lianmengId) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        query.addCriteria(Criteria.where("gameTable.lianmengId").is(lianmengId));
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, GameMemberTable.class);
    }

}
