package com.anbang.qipai.doudizhu.cqrs.q.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestInfoDbo;

public interface GameLatestInfoDboRepository extends MongoRepository<GameLatestInfoDbo, String> {

}
