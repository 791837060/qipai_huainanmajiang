package com.anbang.qipai.admin.plan.service.gameservice;

import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.dao.gamedao.GameHistoricalPanResultDao;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameHistoricalPanResultService {

	@Autowired
	private GameHistoricalPanResultDao majiangHistoricalPanResultDao;

	public void addGameHistoricalResult(GameHistoricalPanResult result) {
		majiangHistoricalPanResultDao.addGameHistoricalResult(result);
	}

	public ListPage findGameHistoricalResultByMemberId(int page, int size, String gameId, Game game) {
		long amount = majiangHistoricalPanResultDao.getAmountByGameIdAndGame(gameId, game);
		List<GameHistoricalPanResult> list = majiangHistoricalPanResultDao.findGameHistoricalResultByGameIdAndGame(page,
				size, gameId, game);
		ListPage listPage = new ListPage(list, page, size, (int) amount);
		return listPage;
	}
}
