package com.anbang.qipai.huainanmajiang.cqrs.q.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.huainanmajiang.cqrs.q.dbo.PanResultDbo;

public interface PanResultDboRepository extends MongoRepository<PanResultDbo, String> {

	PanResultDbo findOneByGameIdAndPanNo(String gameId, int panNo);

}