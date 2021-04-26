package com.anbang.qipai.admin.msg.receiver.memberreceiver;

import com.anbang.qipai.admin.msg.channel.sink.MembersSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.members.MemberDbo;
import com.anbang.qipai.admin.plan.bean.report.DetailedReport;
import com.anbang.qipai.admin.plan.service.membersservice.MemberDboService;
import com.anbang.qipai.admin.plan.service.reportservice.DetailedReportService;
import com.anbang.qipai.admin.util.IPAddressUtil;
import com.anbang.qipai.admin.util.TimeUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MembersSink.class)
public class MembersMsgReceiver {

    Logger logger = LoggerFactory.getLogger(MembersMsgReceiver.class);
    @Autowired
    private MemberDboService memberService;

    @Autowired
    private DetailedReportService reportService;

    private Gson gson = new Gson();

    @StreamListener(MembersSink.MEMBERS)
    public void recordMember(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        MemberDbo member = gson.fromJson(json, MemberDbo.class);
        try {
            if ("newMember".equals(msg)) {
                memberService.addMember(member);
                // 新用户查询注册ip归属地
                if (StringUtils.isNotBlank(member.getReqIP())) {
                    String reqIpAdress = IPAddressUtil.getIPAddress1(member.getReqIP());
                    member.setReqIpAddress(reqIpAdress);
                    memberService.updateIPAddress(member.getId(), reqIpAdress);
                }
                // 报表功能,统计新增用户和用户总量
                // createTime转化为以天为精度的时间戳
                Long createTime = TimeUtil.getTimeWithDayPrecision(member.getCreateTime());
                // 得到用户总量
                int totalUserCount = (int) memberService.countAmount();
                // 组装detailedReport存入数据库,新增用户字段自增一
                DetailedReport report = new DetailedReport(createTime);
                report.setTotalUserCount(totalUserCount);
                reportService.upsertAddUserData(report);
            }
            if ("new robot".equals(msg)) {
                memberService.addMember(member);
            }
            if ("update member phone".equals(msg)) {
                memberService.updateMemberPhone(member);
            }
            if ("update member info".equals(msg)) {
                memberService.updateMemberBaseInfo(member);
            }
            if ("update member vip".equals(msg)) {
                memberService.updateMemberVip(member);
            }
            if ("memberOrder delive".equals(msg)) {
                memberService.memberOrderDelive(member);
            }
            if ("recharge vip".equals(msg)) {
                memberService.rechargeVip(member);
            }
            if ("update member realUser".equals(msg)) {
                memberService.updateMemberRealUser(member);
            }
            if ("update member bindAgent".equals(msg)) {
                memberService.updateMemberBindAgent(member.getId(), member.getAgentId(), member.isBindAgent());
            }
            if ("remove member bindAgent".equals(msg)) {
                memberService.removeMemberBindAgent(member.getId());
            }
            if ("add member bindAgent".equals(msg)) {
                memberService.updateMemberBindAgent(member.getId(), member.getAgentId(), member.isBindAgent());
            }
            if ("update member dalianmeng apply".equals(msg)){
                memberService.updateMemberDalianmengApply(member.getId(), member.isDalianmeng(), member.isQinyouquan());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
