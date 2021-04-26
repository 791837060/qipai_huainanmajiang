package com.anbang.qipai.tuidaohu.cqrs.q.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.tuidaohu.cqrs.q.dbo.MajiangGameDbo;

public interface MajiangGameDboRepository extends MongoRepository<MajiangGameDbo, String> {

}
