package com.anbang.qipai.admin.plan.dao.mongodb.repository;

import com.anbang.qipai.admin.plan.bean.games.GameRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRoomRepository extends MongoRepository<GameRoom, String> {

	GameRoom findByNoAndFinished(String no, boolean finished);

}
