package com.anbang.qipai.game.plan.service;

import com.anbang.qipai.game.plan.bean.games.Game;
import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.dao.GameHistoricalJuResultDao;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameHistoricalJuResultService {

    @Autowired
    private GameHistoricalJuResultDao majiangHistoricalResultDao;

    public void addGameHistoricalResult(GameHistoricalJuResult result) {
        majiangHistoricalResultDao.addGameHistoricalResult(result);
    }

    public ListPage findGameHistoricalResultByMemberId(int page, int size, String memberId, long startTime, long endTime) {
        long amount = majiangHistoricalResultDao.getAmountByMemberIdAndTime(memberId, startTime, endTime);
        List<GameHistoricalJuResult> list = majiangHistoricalResultDao.findGameHistoricalResultByMemberIdAndTime(page, size,
                memberId, startTime, endTime);
        ListPage listPage = new ListPage(list, page, size, (int) amount);
        return listPage;
    }

    public GameHistoricalJuResult findGameHistoricalResultById(String id) {
        return majiangHistoricalResultDao.findGameHistoricalResultById(id);
    }

    public int countGameNumByGameAndTime(Game game, long startTime, long endTime) {
        return majiangHistoricalResultDao.countGameNumByGameAndTime(game, startTime, endTime);
    }

    public GameHistoricalJuResult getJuResultByGameId(String gameId) {
        return majiangHistoricalResultDao.getJuResultByGameId(gameId);
    }

    public Map listTotalScore(String memberId, long startTime, long endTime) {
        Integer juCount = 0;
        Double dayScore = 0d;
        Integer dayingjiaCount = 0;

        List<GameHistoricalJuResult> juResultList = majiangHistoricalResultDao.findGameHistoricalResultByMemberIdAndTime(memberId, startTime, endTime);
        if (juResultList != null) {
            juCount = juResultList.size();
            for (GameHistoricalJuResult list : juResultList) {
                if (!StringUtils.isEmpty(memberId) && memberId.equals(list.getDayingjiaId())) {
                    dayingjiaCount = dayingjiaCount + 1;
                }
                if (list.getPlayerResultList() != null) {
                    for (GameJuPlayerResult juPlayer : list.getPlayerResultList()) {
                        if (memberId.equals(juPlayer.playerId())) {
                            dayScore = dayScore + juPlayer.totalScore();
                        }
                    }
                }
            }
        }
        Map data = new HashMap<>();
        data.put("juCount", juCount);
        data.put("dayScore", dayScore);
        data.put("dayingjiaCount", dayingjiaCount);
        return data;
    }
}
