package com.anbang.qipai.qinyouquan.plan.dao;


import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameTable;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalJuResult;

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

    List<GameTable> findGameTableByMemberIdAndLianmengIdAndGameAndTime(int page, int size, String memberId, String lianmengId,Game game, long startTime, long endTime,String no,String state);

    long countGameTableByMemberIdAndLianmengIdAndGameAndTime(String memberId, String lianmengId, long startTime, long endTime,String no,String state);

    void deleteGameTableByNo(String roomNo);

    void updateGameMemberTable(String gameId);

    List<GameTable> findGameTableByMemberId(int page,int size,String memberId,long startTime,long endTime);

    GameHistoricalJuResult getJuResultByRoomNo(int page,int size,String gameId);

    GameTable findGameTableByRoomNo(int page,int size,String gameId,String memberId);

}
