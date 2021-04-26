package com.anbang.qipai.admin.plan.dao.gamedao;

import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.admin.plan.bean.games.Game;

import java.util.List;

public interface GameHistoricalJuResultDao {

	void addGameHistoricalResult(GameHistoricalJuResult result);

	List<GameHistoricalJuResult> findGameHistoricalResultByMemberId(int page, int size, String memberId);

	long getAmountByMemberId(String memberId);

	int countGameNumByGameAndTime(Game game, long startTime, long endTime);

	GameHistoricalJuResult findGameHistoricalResultById(String id);

	GameHistoricalJuResult findJuResultBygameId(String gameId);
}
