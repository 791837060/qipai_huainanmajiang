package com.anbang.qipai.admin.plan.dao.gamedao;


import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.admin.plan.bean.games.Game;

import java.util.List;

public interface GameHistoricalPanResultDao {

	void addGameHistoricalResult(GameHistoricalPanResult result);

	List<GameHistoricalPanResult> findGameHistoricalResultByGameIdAndGame(int page, int size, String gameId, Game game);

	long getAmountByGameIdAndGame(String gameId, Game game);

	List<GameHistoricalPanResult> findPanResultByGameId(String gameId);
}
