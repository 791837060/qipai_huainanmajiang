package com.anbang.qipai.game.plan.dao;

import java.util.List;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;

public interface GameHistoricalJuResultDao {

	void addGameHistoricalResult(GameHistoricalJuResult result);

	List<GameHistoricalJuResult> findGameHistoricalResultByMemberIdAndTime(int page, int size, String memberId,long startTime,long endTime);

	long getAmountByMemberIdAndTime(String memberId,long startTime,long endTime);

	int countGameNumByGameAndTime(Game game, long startTime, long endTime);

	GameHistoricalJuResult findGameHistoricalResultById(String id);

	GameHistoricalJuResult getJuResultByGameId(String gameId);

	List<GameHistoricalJuResult> findGameHistoricalResultByMemberIdAndTime(String memberId, long startTime, long endTime);

	void removeByTime(long endTime);
}
