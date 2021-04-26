package com.anbang.qipai.admin.plan.dao.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.games.GameServer;

public interface GameServerRepository extends MongoRepository<GameServer, String> {

	List<GameServer> findByGame(Game game);

}
