package com.anbang.qipai.guandan.plan.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.guandan.plan.bean.PlayerInfo;

public interface PlayerInfoRepository extends MongoRepository<PlayerInfo, String> {

}
