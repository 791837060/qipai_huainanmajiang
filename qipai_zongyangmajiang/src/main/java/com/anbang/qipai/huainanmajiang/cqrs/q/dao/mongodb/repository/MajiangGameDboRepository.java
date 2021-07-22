package com.anbang.qipai.huainanmajiang.cqrs.q.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.huainanmajiang.cqrs.q.dbo.MajiangGameDbo;

public interface MajiangGameDboRepository extends MongoRepository<MajiangGameDbo, String> {

}
