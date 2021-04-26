package com.anbang.qipai.qinyouquan.plan.dao.mongodb.repository;

import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameLaw;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameLawRepository extends MongoRepository<GameLaw, String> {

	GameLaw findOneByGameAndName(Game game, String name);

}
