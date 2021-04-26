package com.anbang.qipai.admin.plan.dao.reportdao;

import com.anbang.qipai.admin.plan.bean.report.DetailedReport;

import java.util.List;

/**
 * @author YaphetS
 * @date 2018/11/26
 */
public interface DetailedReportDao {

    /**
     * 更新明细表(暂时没用)
     * @param detailedReport
     */
    void upsert(DetailedReport detailedReport);

    /**
     * 查询明细表某个日期的数据
     * @param dayStartTime
     * @return
     */
    DetailedReport findByCreateTime(long dayStartTime);

    /**
     * 更新明细表的在线人数明细数据
     * @param detailedReport
     */
    void updateByOnline(DetailedReport detailedReport);

    /**
     * 给定开始时间和结束时间,查询明细记录
     * @param startTime
     * @param endTime
     * @return
     */
    List<DetailedReport> findByTime(Long startTime, Long endTime);

    /**
     * 更新OnlineCount,MaxOnlineTime字段
     * @param detailedReport
     */
    void upsertOnlineData(DetailedReport detailedReport);

    /**
     * 更新loginUser字段
     * @param detailedReport
     */
    void upsertLoginUser(DetailedReport detailedReport);

    /**
     * 启动次数自增1次
     * @param detailedReport
     */
    void incPowerCount(DetailedReport detailedReport);

    /**
     * 查询某个时间点后的明细数据
     * @param startTime
     * @return
     */
    List<DetailedReport> findDetailedReportAfterTime(long startTime);

    /**
     * 更新ActiveUser和DayOnlineTime
     * @param detailedReport
     */
    void upsertActiveUserAndDayOnlineTime(DetailedReport detailedReport);



    /**
     * 更新新增用户数(自增一)和用户总量
     * @param report
     */
    void upsertAddUserCountAndTotalUserCount(DetailedReport report);
}
