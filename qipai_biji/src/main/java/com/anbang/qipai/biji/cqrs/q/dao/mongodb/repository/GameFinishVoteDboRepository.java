package com.anbang.qipai.biji.cqrs.q.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.biji.cqrs.q.dbo.GameFinishVoteDbo;

public interface GameFinishVoteDboRepository extends MongoRepository<GameFinishVoteDbo, String> {

	GameFinishVoteDbo findOneByGameId(String gameId);

}
