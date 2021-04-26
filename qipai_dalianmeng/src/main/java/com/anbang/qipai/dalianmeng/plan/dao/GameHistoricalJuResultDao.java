package com.anbang.qipai.dalianmeng.plan.dao;


import com.anbang.qipai.dalianmeng.cqrs.q.dbo.Identity;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameHistoricalJuResult;
import jdk.nashorn.internal.ir.IfNode;

import java.util.List;

public interface GameHistoricalJuResultDao {

    void addGameHistoricalResult(GameHistoricalJuResult result);

    List<GameHistoricalJuResult> findGameHistoricalResultByLianmengIdAndMemberIdAndTime(int page, int size, Game game, String lianmengId, String memberId, long startTime, long endTime);

    long getAmountByLianmengIdAndMemberIdAndTime(Game game, String lianmengId, String memberId, long startTime, long endTime);

    List<GameHistoricalJuResult> findGameHistoricalResultByMemberIdAndTime(int page, int size, String memberId, long startTime, long endTime);

    long getAmountByMemberIdAndTime(String memberId, long startTime, long endTime);

    long countGameNumByGameAndTime(Game game, long startTime, long endTime);

    long countDayingjiaByMemberIdAndTime(String memberId, String lianmengId, long startTime, long endTime);

    GameHistoricalJuResult findGameHistoricalResultById(String id);

    List<GameHistoricalJuResult> findGameHistoricalResultByMemberIdAndLianmengIdAndRoomNoAndTime(int page, int size,
                                                                                                 String lianmengId, String memberId, String roomNo, long startTime, long endTime);

    long getAmountByMemberIdAndLianmengIdAndAndRoomNoTime(String lianmengId, String memberId, String roomNo,
                                                          long startTime, long endTime);

    GameHistoricalJuResult getJuResultByGameId(String gameId);

    List<GameHistoricalJuResult> findGameHistoricalResultByMemberIdAndTime(Game game,String memberId, String lianmengId, long startTime, long endTime);

    void removeByTime(long endTime);

    long countLianmengTotalGames(String lianmengId, String memberId, Identity identity,long startTime, long endTime);

    long countLianmengTotalGamesByMemberIdAndLianMemberId(List<String> memberIdS, String lianmengId, long startTime, long endTime);

    long countgameBymember(String memberId, String lianmengId, long startTime, long endTime);

    long getAmountByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(String lianmengId,List<String> memberId,  long startTime,
                                                                          long endTime );

    List<GameHistoricalJuResult> findGameHistoricalResultByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(int page, int size, String lianmengId,List<String> memberId,
                                                                                                                 long startTime, long endTime);

}
