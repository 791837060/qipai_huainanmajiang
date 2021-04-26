package com.anbang.qipai.admin.plan.service.reportservice;

import com.anbang.qipai.admin.msg.msjobj.MajiangHistoricalJuResultMO;
import com.anbang.qipai.admin.msg.msjobj.PlayerId;
import com.anbang.qipai.admin.plan.bean.report.DetailedReport;
import com.anbang.qipai.admin.plan.bean.report.OnlineTimeReport;
import com.anbang.qipai.admin.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * KAFKA消费局记录
 * @author YaphetS
 * @date 2018/12/6
 */
@Service
public class GameResultMsgService {

    @Autowired
    private OnlineTimeReportService onlineTimeReportService;

    @Autowired
    private OnlineStateRecordService onlineStateRecordService;

    @Autowired
    private DetailedReportService detailedReportService;


    public void recordGameResult(MajiangHistoricalJuResultMO result) {
        //得到以天为精度的创建时间戳
        Long createTime= TimeUtil.getTimeWithDayPrecision(result.getFinishTime());
        //组装OnlineTimeReport的集合
        List<OnlineTimeReport> reportList=new ArrayList<>();
        for(PlayerId playerId:result.getPlayerResultList()){
            OnlineTimeReport report=new OnlineTimeReport();
            //组装createTime(以天为精度)
            report.setCreateTime(createTime);
            //组装memberId
            report.setMemberId(playerId.getPlayerId());
            //组装在线时长
            report.setOnlineTime(onlineStateRecordService.findOnlineTimeByMemberId(playerId.getPlayerId(),createTime));
            reportList.add(report);
        }
        //将OnlineTimeReport的集合upsert进活跃用户在线时长表
        for(OnlineTimeReport report:reportList){
            onlineTimeReportService.upsertReport(report);
        }
        //更新明细表的ActiveUser,DayOnlineTime两字段
        detailedReportService.upsertActiveData(new DetailedReport(createTime));
    }



}
