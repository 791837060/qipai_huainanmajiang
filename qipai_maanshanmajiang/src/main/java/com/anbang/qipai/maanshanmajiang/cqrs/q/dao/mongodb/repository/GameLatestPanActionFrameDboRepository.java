package com.anbang.qipai.maanshanmajiang.cqrs.q.dao.mongodb.repository;

import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameLatestPanActionFrameDboRepository extends MongoRepository<GameLatestPanActionFrameDbo, String> {

}
