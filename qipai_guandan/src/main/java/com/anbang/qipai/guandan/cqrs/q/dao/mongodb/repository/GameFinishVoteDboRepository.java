package com.anbang.qipai.guandan.cqrs.q.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.guandan.cqrs.q.dbo.GameFinishVoteDbo;

public interface GameFinishVoteDboRepository extends MongoRepository<GameFinishVoteDbo, String> {

	GameFinishVoteDbo findOneByGameId(String gameId);

}
