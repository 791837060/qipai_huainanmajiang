package com.anbang.qipai.guandan.cqrs.q.dao.mongodb;

import com.anbang.qipai.guandan.cqrs.q.dao.PukeGameInfoDboDao;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameInfoDbo;
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
public class MongodbPukeGameInfoDboDao implements PukeGameInfoDboDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(PukeGameInfoDbo dbo) {
        mongoTemplate.insert(dbo);
    }

    @Override
    public List<PukeGameInfoDbo> findByGameIdAndPanNo(String gameId, int panNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("gameId").is(gameId));
        query.addCriteria(Criteria.where("panNo").is(panNo));
        query.with(new Sort(new Order(Direction.ASC, "actionNo")));
        return mongoTemplate.find(query, PukeGameInfoDbo.class);
    }

    @Override
    public void removeByTime(long endTime) {
        mongoTemplate.remove(new Query(Criteria.where("createTime").lt(endTime)), PukeGameInfoDbo.class);
    }

}
