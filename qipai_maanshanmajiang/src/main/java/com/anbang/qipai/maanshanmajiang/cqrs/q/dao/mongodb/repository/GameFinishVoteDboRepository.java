package com.anbang.qipai.maanshanmajiang.cqrs.q.dao.mongodb.repository;

import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.GameFinishVoteDbo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameFinishVoteDboRepository extends MongoRepository<GameFinishVoteDbo, String> {

	GameFinishVoteDbo findOneByGameId(String gameId);

}
