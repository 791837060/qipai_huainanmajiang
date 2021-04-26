package com.anbang.qipai.dalianmeng.plan.service;

import com.anbang.qipai.dalianmeng.plan.bean.GameDataReport;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.dao.GameReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameReportService {

    @Autowired
    private GameReportDao gameReportDao;

    public List<GameDataReport> findGameReportByTimeAndGame(long startTime, long endTime, Game game) {
        List<GameDataReport> reportList = gameReportDao.findReportByTime(startTime, endTime, game);
        return reportList;
    }

    public void addGameReport(GameDataReport report) {
        gameReportDao.addReport(report);
    }

    public int countGameNumByTime(long startTime, long endTime) {
        return gameReportDao.countGameNumByTime(startTime, endTime);
    }
}
