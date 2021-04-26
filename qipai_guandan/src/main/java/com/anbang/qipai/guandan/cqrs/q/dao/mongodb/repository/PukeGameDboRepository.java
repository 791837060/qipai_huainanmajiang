package com.anbang.qipai.guandan.cqrs.q.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameDbo;

public interface PukeGameDboRepository extends MongoRepository<PukeGameDbo, String> {

}
