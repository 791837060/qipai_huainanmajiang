package com.anbang.qipai.maanshanmajiang.cqrs.q.dao.mongodb.repository;

import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.JuResultDbo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JuResultDboRepository extends MongoRepository<JuResultDbo, String> {

	JuResultDbo findOneByGameId(String gameId);

}
