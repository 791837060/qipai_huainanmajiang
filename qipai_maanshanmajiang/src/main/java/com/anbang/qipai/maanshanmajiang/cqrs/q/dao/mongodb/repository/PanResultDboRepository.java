package com.anbang.qipai.maanshanmajiang.cqrs.q.dao.mongodb.repository;

import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.PanResultDbo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PanResultDboRepository extends MongoRepository<PanResultDbo, String> {

	PanResultDbo findOneByGameIdAndPanNo(String gameId, int panNo);

}
