package com.anbang.qipai.shouxianmajiang.cqrs.q.dao.mongodb.repository;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.MajiangGameDbo;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.MajiangGameDbo;

public interface MajiangGameDboRepository extends MongoRepository<MajiangGameDbo, String> {

}
