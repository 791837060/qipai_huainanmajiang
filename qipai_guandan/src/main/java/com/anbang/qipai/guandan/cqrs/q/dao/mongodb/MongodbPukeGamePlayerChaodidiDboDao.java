package com.anbang.qipai.guandan.cqrs.q.dao.mongodb;

import com.anbang.qipai.guandan.cqrs.c.domain.state.PukeGamePlayerChaodiState;
import com.anbang.qipai.guandan.cqrs.q.dao.PukeGamePlayerChaodiDboDao;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGamePlayerChaodiDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MongodbPukeGamePlayerChaodidiDboDao implements PukeGamePlayerChaodiDboDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addPukeGamePlayerChaodiDbo(PukeGamePlayerChaodiDbo dbo) {
        mongoTemplate.insert(dbo);
    }

    @Override
    public void updatePukeGamePlayerChaodiDbo(String gameId, int panNo,
                                              Map<String, PukeGamePlayerChaodiState> playerChaodiStateMap) {
        Query query = new Query();
        query.addCriteria(Criteria.where("gameId").is(gameId));
        query.addCriteria(Criteria.where("panNo").is(panNo));
        Update update = new Update();
        update.set("playerChaodiStateMap", playerChaodiStateMap);
        mongoTemplate.updateFirst(query, update, PukeGamePlayerChaodiDbo.class);
    }

    @Override
    public PukeGamePlayerChaodiDbo findLastByGameId(String gameId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("gameId").is(gameId));
        Sort sort = new Sort(new Order(Direction.DESC, "panNo"));
        query.with(sort);
        return mongoTemplate.findOne(query, PukeGamePlayerChaodiDbo.class);
    }

    @Override
    public PukeGamePlayerChaodiDbo findByGameIdAndPanNo(String gameId, int panNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("gameId").is(gameId));
        query.addCriteria(Criteria.where("panNo").is(panNo));
        return mongoTemplate.findOne(query, PukeGamePlayerChaodiDbo.class);
    }

    @Override
    public void removeByTime(long endTime) {
        mongoTemplate.remove(new Query(Criteria.where("createTime").lt(endTime)), PukeGamePlayerChaodiDbo.class);
    }

}
