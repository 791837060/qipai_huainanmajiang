package com.anbang.qipai.dalianmeng.plan.dao.mongodb.repository;


import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameMemberTable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberGameTableRepository extends MongoRepository<GameMemberTable, String> {

    void deleteByMemberIdAndGameTableGameAndGameTableServerGameGameId(String memberId, Game game, String serverGameId);

    void deleteByGameTableGameAndGameTableServerGameGameId(Game game, String serverGameId);
}
