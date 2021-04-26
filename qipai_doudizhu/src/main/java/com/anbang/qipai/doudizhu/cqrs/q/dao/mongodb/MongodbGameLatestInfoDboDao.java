package com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb;

import com.anbang.qipai.doudizhu.cqrs.q.dao.GameLatestInfoDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb.repository.GameLatestInfoDboRepository;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestInfoDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MongodbGameLatestInfoDboDao implements GameLatestInfoDboDao {
    @Autowired
    private GameLatestInfoDboRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(GameLatestInfoDbo info) {
        repository.save(info);
    }

    @Override
    public GameLatestInfoDbo findById(String gameId) {
        return repository.findOne(gameId);
    }

    @Override
    public void removeByTime(long endTime) {
        mongoTemplate.remove(new Query(Criteria.where("createTime").lt(endTime)), GameLatestInfoDbo.class);
    }

}
