package com.anbang.qipai.admin.plan.dao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.report.PlatformReport;

public interface PlatformReportDao {
	List<PlatformReport> findReportByTime(int page, int size, long startTime, long endTime);

	long getAmountByTime(long startTime, long endTime);

	void addReport(PlatformReport report);

    List<PlatformReport> findAllReportByTime(Long startTime, Long endTime);
}
