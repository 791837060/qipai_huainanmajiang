package com.anbang.qipai.admin.publisher.notifier;

import com.anbang.qipai.admin.plan.bean.report.BasicDataReport;
import com.anbang.qipai.admin.plan.bean.report.DetailedReport;
import com.anbang.qipai.admin.plan.bean.report.OnlineStateRecord;
import com.anbang.qipai.admin.plan.bean.report.OnlineTimeReport;
import com.anbang.qipai.admin.plan.dao.reportdao.BasicDataReportDao;
import com.anbang.qipai.admin.plan.dao.reportdao.OnlineStateRecordDao;
import com.anbang.qipai.admin.plan.service.membersservice.MemberDboService;
import com.anbang.qipai.admin.plan.service.reportservice.BasicDataReportService;
import com.anbang.qipai.admin.plan.service.reportservice.DetailedReportService;
import com.anbang.qipai.admin.plan.service.reportservice.OnlineStateRecordService;
import com.anbang.qipai.admin.plan.service.reportservice.OnlineTimeReportService;
import com.anbang.qipai.admin.publisher.EntityCreatedEvent;
import com.anbang.qipai.admin.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author YaphetS
 * @date 2018/11/24
 */
@Component
public class OnlineRecordNotifier {
    private final static int ON_LINE_ACTION=1;
    private final static int OFF_LINE_ACTION=-1;

    @Autowired
    private OnlineStateRecordService onlineStateRecordService;

    @Autowired
    private BasicDataReportService basicDataReportService;

    @Autowired
    private MemberDboService memberDboService;

    @Autowired
    private DetailedReportService detailedReportService;

    @Autowired
    private OnlineTimeReportService onlineTimeReportService;

    @EventListener
    @Async
    public void processContentEvent(EntityCreatedEvent<OnlineStateRecord> event) {

        OnlineStateRecord onlineStateRecord=(OnlineStateRecord)event.getSource();
        //添加上下线记录
        onlineStateRecordService.insert(onlineStateRecord);
        //得到以天为精度的时间戳,下面两个操作都要用
        long dayStartTime=TimeUtil.getTimeWithDayPrecision(onlineStateRecord.getCreateTime());
        //如果是上线操作,更新DetailedReport的loginUser字段,powerCount字段
        if(onlineStateRecord.getOnlineState()==OnlineStateRecord.ON_LINE){
            DetailedReport detailedReport=new DetailedReport(
                    dayStartTime, onlineStateRecordService.countOnlineRecordAfterTime(dayStartTime));
            detailedReportService.upsertLoginUser(detailedReport);
            detailedReportService.incPowerCount(detailedReport);
        }

        //如果是下线操作,	判断是否是活跃用户,如果是upsert->onlineTime in 活跃用户日在线时长表
        if(onlineStateRecord.getOnlineState()==OnlineStateRecord.OFF_LINE){
            OnlineTimeReport report=onlineTimeReportService.find(dayStartTime,onlineStateRecord.getMemberId());
            if(report!=null){
                report.setOnlineTime(onlineStateRecordService.findOnlineTimeByMemberId(onlineStateRecord.getMemberId(),dayStartTime));
                onlineTimeReportService.upsertReport(report);
                //更新明细表的ActiveUser,DayOnlineTime两字段
                detailedReportService.upsertActiveData(new DetailedReport(dayStartTime));
            }
        }

        //得到以小时为精度的时间戳
        long createTime= TimeUtil.getTimeWithHourPrecision(onlineStateRecord.getCreateTime());
        BasicDataReport report=basicDataReportService.findByCreateTime(createTime);

        if(report==null){
            //当前时间的记录不存在
            //在线人数
            Integer onlineCount=memberDboService.countOnlineState();
            //（开始时间，当前在线，最高在线（跟上个字段数据一致））
            //为了防止并发,使用upsert
            BasicDataReport basicDataReport=new BasicDataReport(createTime,onlineCount,onlineCount);
            basicDataReportService.upsert(basicDataReport);

            //根据每小时在线人数更新明细表数据
            detailedReportService.updateDetailedReport(basicDataReport);
        }else{
            //当前时间的记录存在
            Integer onlineState=onlineStateRecord.getOnlineState();

            if(onlineState==OnlineStateRecord.ON_LINE){
                //$inc,当前在线+1
                basicDataReportService.incCurrentQuantity(report,ON_LINE_ACTION);
                //为了防止并发,根据开始时间查出这条数据,如果当前在线>最高在线,最高在线=当前在线,存入数据库
                BasicDataReport basicDataReport=basicDataReportService.findByCreateTime(createTime);
                if(basicDataReport.getCurrentQuantity()>basicDataReport.getMaxQuantity()){
                    basicDataReport.setMaxQuantity(basicDataReport.getCurrentQuantity());
                    basicDataReportService.updateMaxQuantity(basicDataReport);

                    //根据每小时在线人数更新明细表数据
                    detailedReportService.updateDetailedReport(basicDataReport);
                }
            }else if(onlineState==OnlineStateRecord.OFF_LINE){
                //$inc,当前在线-1
                basicDataReportService.incCurrentQuantity(report,OFF_LINE_ACTION);
            }
        }
    }
}
