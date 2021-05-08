package com.anbang.qipai.shouxianmajiang.cqrs.q.dao.mongodb.repository;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.GameLatestPanActionFrameDbo;

public interface GameLatestPanActionFrameDboRepository extends MongoRepository<GameLatestPanActionFrameDbo, String> {

}
