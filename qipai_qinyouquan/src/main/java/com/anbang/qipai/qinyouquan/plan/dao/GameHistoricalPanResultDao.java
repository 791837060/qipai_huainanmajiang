package com.anbang.qipai.qinyouquan.plan.dao;


import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalPanResult;

import java.util.List;

public interface GameHistoricalPanResultDao {

	void addGameHistoricalResult(GameHistoricalPanResult result);

	List<GameHistoricalPanResult> findGameHistoricalResultByGameIdAndGame(int page, int size, String gameId, Game game);

	long getAmountByGameIdAndGame(String gameId, Game game);

	void removeByTime(long endTime);

	List<GameHistoricalPanResult> findPanResultByGameId(String gameId);
}
