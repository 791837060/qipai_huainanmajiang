package com.anbang.qipai.admin.plan.dao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.report.GameDataReport;

public interface GameReportDao {
	List<GameDataReport> findReportByTime(long startTime, long endTime, Game game);

	void addReport(GameDataReport report);

	int countGameNumByTime(long startTime, long endTime);
}
