package com.anbang.qipai.dalianmeng.plan.dao;


import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameHistoricalPanResult;

import java.util.List;

public interface GameHistoricalPanResultDao {

	void addGameHistoricalResult(GameHistoricalPanResult result);

	List<GameHistoricalPanResult> findGameHistoricalResultByGameIdAndGame(int page, int size, String gameId, Game game);

	long getAmountByGameIdAndGame(String gameId, Game game);

	void removeByTime(long endTime);

	List<GameHistoricalPanResult> findPanResultByGameId(String gameId);
}
