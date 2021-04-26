package com.anbang.qipai.qinyouquan.plan.dao;

import com.anbang.qipai.qinyouquan.plan.bean.GameDataReport;
import com.anbang.qipai.qinyouquan.plan.bean.game.Game;

import java.util.List;

public interface GameReportDao {
    List<GameDataReport> findReportByTime(long startTime, long endTime, Game game);

    void addReport(GameDataReport report);

    int countGameNumByTime(long startTime, long endTime);
}
