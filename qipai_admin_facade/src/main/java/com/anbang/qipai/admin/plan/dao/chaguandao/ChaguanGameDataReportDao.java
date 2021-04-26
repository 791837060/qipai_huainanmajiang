package com.anbang.qipai.admin.plan.dao.chaguandao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanGameDataReport;
import com.anbang.qipai.admin.plan.bean.games.Game;

public interface ChaguanGameDataReportDao {

	List<ChaguanGameDataReport> findReportByTime(long startTime, long endTime, Game game);

	void addReport(ChaguanGameDataReport report);

	int countGameNumByTime(long startTime, long endTime);
}
