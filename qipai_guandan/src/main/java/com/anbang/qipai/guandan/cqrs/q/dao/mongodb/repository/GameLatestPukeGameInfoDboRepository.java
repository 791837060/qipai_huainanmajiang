package com.anbang.qipai.guandan.cqrs.q.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.guandan.cqrs.q.dbo.GameLatestPukeGameInfoDbo;

public interface GameLatestPukeGameInfoDboRepository extends MongoRepository<GameLatestPukeGameInfoDbo, String> {

}
