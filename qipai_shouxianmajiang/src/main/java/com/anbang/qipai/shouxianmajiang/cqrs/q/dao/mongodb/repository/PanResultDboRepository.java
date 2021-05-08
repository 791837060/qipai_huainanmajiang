package com.anbang.qipai.shouxianmajiang.cqrs.q.dao.mongodb.repository;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.PanResultDbo;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.PanResultDbo;

public interface PanResultDboRepository extends MongoRepository<PanResultDbo, String> {

	PanResultDbo findOneByGameIdAndPanNo(String gameId, int panNo);

}
