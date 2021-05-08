package com.anbang.qipai.shouxianmajiang.cqrs.q.dao.mongodb.repository;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.JuResultDbo;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.JuResultDbo;

public interface JuResultDboRepository extends MongoRepository<JuResultDbo, String> {

	JuResultDbo findOneByGameId(String gameId);

}
