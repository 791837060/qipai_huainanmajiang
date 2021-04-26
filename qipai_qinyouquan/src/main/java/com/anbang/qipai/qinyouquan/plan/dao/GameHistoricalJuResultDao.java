package com.anbang.qipai.qinyouquan.plan.dao;


import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalJuResult;

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

    GameHistoricalJuResult getJuResultByGameId(String gameId);

    List<GameHistoricalJuResult> findGameHistoricalResultByMemberIdAndTime(Game game, String memberId, String lianmengId, long startTime, long endTime);

    void memberJuResultFinish(String memberId, String lianmengId, long startTime, long endTime);

    List<GameHistoricalJuResult> findGameHistoricalResultByLianmengIdAndSuperiorMemberIdAndTime(int page, int size, String lianmengId,
                                                                                                String superiorMemberId, long startTime, long endTime, boolean finish);

    long getAmountByLianmengIdAndSuperiorMemberIdAndTime(String lianmengId, String superiorMemberId, long startTime, long endTime, boolean finish);

    void juResultFinish(String gameId);


    List<GameHistoricalJuResult> findGameHistoricalResultByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(int page, int size, String lianmengId,String memberId,
                                                                                                String superiorMemberId, long startTime, long endTime);

    long getAmountByLianmengIdAndSuperiorMemberIdAndSearchMemberIdAndTime(String lianmengId,String memberId, String superiorMemberId, long startTime,
                                                                          long endTime );

    List<GameHistoricalJuResult> findGameHistoricalResultByTime(String lianmengId, long startTime, long endTime);

    List<GameHistoricalJuResult> findGameHistoricalResultByLianmengIdAndMemberIdAndTime(int page, int size, String lianmengId,
                                                                                                String searchMemberId, long startTime, long endTime, boolean finish);

    long getAmountByLianmengIdAndMemberIdAndTime(String lianmengId, String searchMemberId, long startTime, long endTime, boolean finish);

    List<GameHistoricalJuResult> queryLianmengGoldCost(String lianmengId,Game game,Long startTime, Long endTime);

    List<GameHistoricalJuResult> querymemberGoldRecord(int page,int size,String memberId);

    List<GameHistoricalJuResult> queryHistoricalJuResult(long startTime,long endTime);
}
