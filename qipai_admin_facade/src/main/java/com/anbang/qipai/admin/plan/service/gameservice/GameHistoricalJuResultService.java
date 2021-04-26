package com.anbang.qipai.admin.plan.service.gameservice;


import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.dao.gamedao.GameHistoricalJuResultDao;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameHistoricalJuResultService {

	@Autowired
	private GameHistoricalJuResultDao majiangHistoricalResultDao;

	public void addGameHistoricalResult(GameHistoricalJuResult result) {
		majiangHistoricalResultDao.addGameHistoricalResult(result);
	}

	public ListPage findGameHistoricalResultByMemberId(int page, int size, String memberId) {
		long amount = majiangHistoricalResultDao.getAmountByMemberId(memberId);
		List<GameHistoricalJuResult> list = majiangHistoricalResultDao.findGameHistoricalResultByMemberId(page, size,
				memberId);
		ListPage listPage = new ListPage(list, page, size, (int) amount);
		return listPage;
	}

	public GameHistoricalJuResult findGameHistoricalResultById(String id) {
		return majiangHistoricalResultDao.findGameHistoricalResultById(id);
	}

	public int countGameNumByGameAndTime(Game game, long startTime, long endTime) {
		return majiangHistoricalResultDao.countGameNumByGameAndTime(game, startTime, endTime);
	}
}
