package com.anbang.qipai.admin.plan.dao.reportdao;

import com.anbang.qipai.admin.plan.bean.report.BasicDataReport;

import java.util.List;

/**
 * @author YaphetS
 * @date 2018/11/24
 */
public interface BasicDataReportDao {
    /**
     * 查找是否已经增加过一条以小时为精度的平均在线时长数据
     * @param createTime
     * @return
     */
    BasicDataReport findByCreateTime(long createTime);

    /**
     * 会出现并发问题!!改用upsert
     * 新增一条以小时为精度的平均在线时长数据
     * @param basicDataReport
     */
    void insert(BasicDataReport basicDataReport);

    /**
     * 新增一条以小时为精度的平均在线时长数据
     * @param basicDataReport
     */
    void upsert(BasicDataReport basicDataReport);

    /**
     * 当前在线字段自增或自减
     * @param basicDataReport
     * @param modify
     */
    void incCurrentQuantity(BasicDataReport basicDataReport,int modify);

    /**
     * 更新最高在线
     * @param basicDataReport
     */
    void updateMaxQuantity(BasicDataReport basicDataReport);

    /**
     * 查询某个时间点后的所有在线统计数据
     * @param startTime
     * @return
     */
    List<BasicDataReport> findBasicDataAfterTime(long startTime);
}
