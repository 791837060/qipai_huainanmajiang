package com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb;

import com.anbang.qipai.doudizhu.cqrs.q.dao.GameInfoDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb.repository.GameInfoDboRepository;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameInfoDbo;
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
public class MongodbGameInfoDboDao implements GameInfoDboDao {

    @Autowired
    private GameInfoDboRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(GameInfoDbo info) {
        repository.save(info);
    }

    @Override
    public List<GameInfoDbo> findByGameIdAndPanNo(String gameId, int panNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("gameId").is(gameId));
        query.addCriteria(Criteria.where("panNo").is(panNo));
        query.with(new Sort(new Order(Direction.ASC, "actionNo")));
        return mongoTemplate.find(query, GameInfoDbo.class);
    }

    @Override
    public void removeByTime(long endTime) {
        mongoTemplate.remove(new Query(Criteria.where("createTime").lt(endTime)), GameInfoDbo.class);
    }

}
