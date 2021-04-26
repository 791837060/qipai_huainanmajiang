package com.anbang.qipai.dalianmeng.plan.service;


import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameTable;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameHistoricalPanResult;
import com.anbang.qipai.dalianmeng.plan.dao.GameHistoricalPanResultDao;
import com.anbang.qipai.dalianmeng.plan.dao.GameTableDao;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameHistoricalPanResultService {

    @Autowired
    private GameHistoricalPanResultDao majiangHistoricalPanResultDao;

    @Autowired
    private GameTableDao gameTableDao;

    public void addGameHistoricalResult(GameHistoricalPanResult result) {
        majiangHistoricalPanResultDao.addGameHistoricalResult(result);
        //盘消息有延迟，需要再更新当前盘数
        int no = (int) majiangHistoricalPanResultDao.getAmountByGameIdAndGame(result.getGameId(), result.getGame());
        gameTableDao.updateGameTableCurrentPanNum(result.getGame(), result.getGameId(), no);
        GameTable table = gameTableDao.findTableByGameAndServerGameGameId(result.getGame(), result.getGameId());
    }

    public ListPage findGameHistoricalResultByGameIdAndGame(int page, int size, String gameId, Game game) {
        long amount = majiangHistoricalPanResultDao.getAmountByGameIdAndGame(gameId, game);
        List<GameHistoricalPanResult> list = majiangHistoricalPanResultDao.findGameHistoricalResultByGameIdAndGame(page,
                size, gameId, game);
        ListPage listPage = new ListPage(list, page, size, (int) amount);
        return listPage;
    }

    public int countFinishPanResultByGameIdAndGame(Game game, String gameId) {
        return (int) majiangHistoricalPanResultDao.getAmountByGameIdAndGame(gameId, game);
    }
}
