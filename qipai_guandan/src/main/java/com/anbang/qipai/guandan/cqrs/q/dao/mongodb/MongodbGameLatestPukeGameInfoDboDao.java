package com.anbang.qipai.guandan.cqrs.q.dao.mongodb;

import com.anbang.qipai.guandan.cqrs.q.dao.GameLatestPukeGameInfoDboDao;
import com.anbang.qipai.guandan.cqrs.q.dao.mongodb.repository.GameLatestPukeGameInfoDboRepository;
import com.anbang.qipai.guandan.cqrs.q.dbo.GameLatestPukeGameInfoDbo;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameInfoDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MongodbGameLatestPukeGameInfoDboDao implements GameLatestPukeGameInfoDboDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GameLatestPukeGameInfoDboRepository repository;

    @Override
    public GameLatestPukeGameInfoDbo findById(String id) {
        return repository.findOne(id);
    }

    @Override
    public void save(String id, PukeGameInfoDbo gameInfoDbo) {
        GameLatestPukeGameInfoDbo dbo = new GameLatestPukeGameInfoDbo();
        dbo.setId(id);
        dbo.setPukeGameInfoDbo(gameInfoDbo);
        dbo.setCreateTime(System.currentTimeMillis());
        repository.save(dbo);
    }

    @Override
    public void removeByTime(long endTime) {
        mongoTemplate.remove(new Query(Criteria.where("createTime").lt(endTime)), GameLatestPukeGameInfoDbo.class);
    }

}
