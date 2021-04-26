package com.anbang.qipai.dalianmeng.plan.dao.mongodb.repository;

import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameLaw;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameLawRepository extends MongoRepository<GameLaw, String> {

	GameLaw findOneByGameAndName(Game game, String name);

}
