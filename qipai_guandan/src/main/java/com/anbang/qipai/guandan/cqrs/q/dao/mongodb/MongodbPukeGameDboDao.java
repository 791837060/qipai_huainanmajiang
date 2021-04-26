package com.anbang.qipai.guandan.cqrs.q.dao.mongodb;

import com.anbang.qipai.guandan.cqrs.q.dao.PukeGameDboDao;
import com.anbang.qipai.guandan.cqrs.q.dao.mongodb.repository.PukeGameDboRepository;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameDbo;
import com.dml.mpgame.game.player.GamePlayerOnlineState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MongodbPukeGameDboDao implements PukeGameDboDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PukeGameDboRepository repository;

    @Override
    public PukeGameDbo findById(String id) {
        return repository.findOne(id);
    }

    @Override
    public void save(PukeGameDbo pukeGameDbo) {
        repository.save(pukeGameDbo);
    }

    @Override
    public void updatePlayerOnlineState(String id, String playerId, GamePlayerOnlineState onlineState) {
        PukeGameDbo pukeGameDbo = repository.findOne(id);
        pukeGameDbo.getPlayers().forEach((player) -> {
            if (player.getPlayerId().equals(playerId)) {
                player.setOnlineState(onlineState);
            }
        });
        repository.save(pukeGameDbo);
    }

    @Override
    public void removeByTime(long endTime) {
        mongoTemplate.remove(new Query(Criteria.where("createTime").lt(endTime)), PukeGameDbo.class);
    }

}
