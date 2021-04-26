package com.anbang.qipai.dalianmeng.plan.dao.mongodb.repository;

import com.anbang.qipai.dalianmeng.plan.bean.game.GameTable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameTableRepository extends MongoRepository<GameTable, String> {

    GameTable findByNoAndState(String no, String state);

}
