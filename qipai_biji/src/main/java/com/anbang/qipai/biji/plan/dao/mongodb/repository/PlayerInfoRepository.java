package com.anbang.qipai.biji.plan.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.biji.plan.bean.PlayerInfo;

public interface PlayerInfoRepository extends MongoRepository<PlayerInfo, String> {

}
