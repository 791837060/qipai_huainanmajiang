package com.anbang.qipai.admin.plan.service.reportservice;

import com.anbang.qipai.admin.plan.bean.report.BasicDataReport;
import com.anbang.qipai.admin.plan.bean.report.DetailedReport;
import com.anbang.qipai.admin.plan.bean.report.OnlineStateRecord;
import com.anbang.qipai.admin.plan.bean.report.OnlineTimeReport;
import com.anbang.qipai.admin.plan.dao.reportdao.DetailedReportDao;
import com.anbang.qipai.admin.plan.service.membersservice.MemberDboService;
import com.anbang.qipai.admin.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YaphetS
 * @date 2018/11/26
 */
@Service
public class DetailedReportService {

    @Autowired
    private DetailedReportDao reportDao;

    @Autowired
    private OnlineTimeReportService onlineTimeReportService;

    @Autowired
    private MemberDboService memberDboService;


    public void upsert(DetailedReport detailedReport) {
        reportDao.upsert(detailedReport);
    }


    public DetailedReport findByCreateTime(long dayStartTime) {
        return reportDao.findByCreateTime(dayStartTime);
    }

    public void updateByOnline(DetailedReport detailedReport) {
        reportDao.updateByOnline(detailedReport);
    }


    public void updateDetailedReport(BasicDataReport basicDataReport) {
        //最高在线修改后,修改明细表(最终生成的数据表)
        //根据时间(日期开始时间)查明细表
        //得到当前日子的开始时间(以天为精度的时间戳)
        long dayStartTime= TimeUtil.getTimeWithDayPrecision(basicDataReport.getCreateTime());
        DetailedReport detailed=findByCreateTime(dayStartTime);
        if(detailed==null){
            //不存在:upsert:添加数据(当前日期(峰值时间),同时在线,峰值时间)
            DetailedReport detailedReport=new DetailedReport();
            detailedReport.setCreateTime(dayStartTime);
            detailedReport.setOnlineCount(basicDataReport.getCurrentQuantity());
            detailedReport.setMaxOnlineTime(basicDataReport.getCreateTime());
            reportDao.upsertOnlineData(detailedReport);
        }else{
            //存在
            //如果detailed.getOnlineCount()为null
            if(detailed.getOnlineCount()==null){
                updateByOnline(
                        new DetailedReport(dayStartTime,basicDataReport.getCurrentQuantity(),basicDataReport.getCreateTime()));
            }
            //如果刚才得到的当前在线>明细表中存在的同时在线
            //update:(同时在线,峰值时间)
            if(basicDataReport.getCurrentQuantity()>detailed.getOnlineCount()){
                updateByOnline(
                        new DetailedReport(dayStartTime,basicDataReport.getCurrentQuantity(),basicDataReport.getCreateTime()));
            }
        }
    }

    public List<DetailedReport> findByTime(Long startTime, Long endTime) {
        return reportDao.findByTime(startTime, endTime);
    }

    public void upsertLoginUser(DetailedReport detailedReport) {
        //加入对totalUserCount的启动次数修正
        detailedReport.setTotalUserCount((int)memberDboService.countAmount());
        reportDao.upsertLoginUser(detailedReport);
    }

    public void incPowerCount(DetailedReport detailedReport) {
        reportDao.incPowerCount(detailedReport);
    }

    public List<DetailedReport> findDetailedReportAfterTime(long startTime) {
        return reportDao.findDetailedReportAfterTime(startTime);
    }

    /**
     * 更新活跃用户和日均在线时长
     * @param detailedReport
     */
    public void upsertActiveData(DetailedReport detailedReport) {
        List<OnlineTimeReport> onlineTimeReportList=onlineTimeReportService.findByTime(detailedReport.getCreateTime());
        detailedReport.setActiveUser(onlineTimeReportList.size());
        detailedReport.setDayOnlineTime(getDayOnlineTime(onlineTimeReportList));
        reportDao.upsertActiveUserAndDayOnlineTime(detailedReport);
    }

    /**
     * 计算日均在线时长
     * @param list
     * @return
     */
    private Long getDayOnlineTime(List<OnlineTimeReport> list){
        long dayOnlineTime=0L;
        for(OnlineTimeReport report:list){
            dayOnlineTime+=report.getOnlineTime();
        }
        return dayOnlineTime/list.size();
    }



    /**
     * 更新新增用户数和用户总量
     * @param report
     */
    public void upsertAddUserData(DetailedReport report) {
        reportDao.upsertAddUserCountAndTotalUserCount(report);
    }
}
