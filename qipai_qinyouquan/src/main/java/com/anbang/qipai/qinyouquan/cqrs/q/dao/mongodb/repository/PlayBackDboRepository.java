package com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb.repository;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.PlayBackDbo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayBackDboRepository extends MongoRepository<PlayBackDbo, String> {

}
