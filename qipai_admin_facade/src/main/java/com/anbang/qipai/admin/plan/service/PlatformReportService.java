package com.anbang.qipai.admin.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.report.PlatformReport;
import com.anbang.qipai.admin.plan.dao.PlatformReportDao;
import com.highto.framework.web.page.ListPage;

@Service
public class PlatformReportService {

	@Autowired
	private PlatformReportDao platformReportDao;

	public ListPage findPlatformReportByTime(int page, int size, long startTime, long endTime) {
		long amount = platformReportDao.getAmountByTime(startTime, endTime);
		List<PlatformReport> reportList = platformReportDao.findReportByTime(page, size, startTime, endTime);
		ListPage listPage = new ListPage(reportList, page, size, (int) amount);
		return listPage;
	}

	public void addPlatformReport(PlatformReport report) {
		platformReportDao.addReport(report);
	}

    /**
     * 在时间段中查询(无分页)
     * @param startTime
     * @param endTime
     * @return
     */
    public List<PlatformReport> findAllPlatformReportByTime(Long startTime, Long endTime) {
        List<PlatformReport> reportList = platformReportDao.findAllReportByTime(startTime, endTime);
	    return reportList;
    }
}
