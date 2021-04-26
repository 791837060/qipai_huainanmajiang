package com.anbang.qipai.admin.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanGameDataReport;
import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.report.GameDataReport;
import com.anbang.qipai.admin.plan.dao.GameReportDao;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanGameDataReportDao;

@Service
public class GameReportService {

	@Autowired
	private GameReportDao gameReportDao;

	@Autowired
	private ChaguanGameDataReportDao chaguanGameDataReportDao;

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

	public List<ChaguanGameDataReport> findChaguanGameDataReportByTimeAndGame(long startTime, long endTime, Game game) {
		List<ChaguanGameDataReport> reportList = chaguanGameDataReportDao.findReportByTime(startTime, endTime, game);
		return reportList;
	}

	public void addChaguanGameDataReport(ChaguanGameDataReport report) {
		chaguanGameDataReportDao.addReport(report);
	}

	public int countChaguanGameNumByTime(long startTime, long endTime) {
		return chaguanGameDataReportDao.countGameNumByTime(startTime, endTime);
	}
}
