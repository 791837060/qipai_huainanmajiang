package com.anbang.qipai.dalianmeng.plan.dao;


import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameTable;

import java.util.List;

public interface GameTableDao {

    void save(GameTable gameTable);

    GameTable findTableOpen(String no);

    List<GameTable> findExpireGameTable(long deadlineTime, String state);

    List<GameTable> findGameTableByLianmengId(String lianmengId, int page, int size);

    GameTable findTableByGameAndServerGameGameId(Game game, String serverGameId);

    void updateStateGameTable(Game game, String serverGameId, String state);

    void updateGameTableCurrentPanNum(Game game, String serverGameId, int no);

    void updateGameTableDeadlineTime(Game game, String serverGameId, long deadlineTime);

    List<GameTable> findGameTableByMemberIdAndLianmengIdAndGameAndTime(int page, int size, String memberId, String lianmengId,Game game,
                                                                       long startTime, long endTime,String no,String state);

    long countGameTableByMemberIdAndLianmengIdAndGameAndTime(String memberId, String lianmengId, long startTime, long endTime,String no,String state);

    void deleteGameTableByNo(String roomNo);

    void updateGameMemberTable(String gameId);

    void removeByTime(long endTime);

}
