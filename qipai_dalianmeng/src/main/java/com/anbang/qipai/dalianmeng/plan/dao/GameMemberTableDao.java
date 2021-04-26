package com.anbang.qipai.dalianmeng.plan.dao;

import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameMemberTable;

import java.util.List;

public interface GameMemberTableDao {

    void save(GameMemberTable gameMemberTable);

    GameMemberTable findByMemberIdAndGameTableId(String memberId,String gameTableId);

    long countMemberByGameAndServerGameId(Game game, String serverGameId);

    List<GameMemberTable> findMemberGameTableByGameAndServerGameId(Game game, String serverGameId);

    void remove(Game game, String serverGameId, String memberId);

    void removeExpireRoom(Game game, String serverGameId);

    void updateMemberGameTableCurrentPanNum(Game game, String serverGameId, List<String> playerIds, int no);

    void deleteGameTableByNo(String roomNo);

    GameMemberTable findByMemberId(String memberId);

    GameMemberTable findByMemberIdAndLianmengId(String memberId, String lianmengId);
}
