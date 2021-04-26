package com.anbang.qipai.admin.web.controller;

import com.anbang.qipai.admin.msg.service.AdminHongbaoAdjustMsgService;
import com.anbang.qipai.admin.plan.bean.hongbao.MemberInvitationRecord;
import com.anbang.qipai.admin.plan.bean.hongbao.MemberInvitationRecordState;
import com.anbang.qipai.admin.plan.service.MemberInvitationRecordService;
import com.anbang.qipai.admin.util.CommonVOUtil;
import com.anbang.qipai.admin.web.query.InvitationQuery;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 红包点任务controller,主要为玩家邀请
 */
@CrossOrigin
@RestController
@RequestMapping("/memberInvitation")
public class MemberInvitationController {
    @Autowired
    private MemberInvitationRecordService memberInvitationRecordService;

    @Autowired
    private AdminHongbaoAdjustMsgService adminHongbaoAdjustMsgService;

    @RequestMapping("/listInvitationRecord")
    public CommonVO listInvitationRecord(InvitationQuery query) {
        if (query.getPage() == null || query.getSize() == null) {
            query.setPage(1);
            query.setSize(10);
        }
        if (StringUtils.isBlank(query.getMemberId()) || StringUtils.isBlank(query.getState())) {
            return CommonVOUtil.lackParameter();
        }
        Map data = new HashMap();
        ListPage listPage = memberInvitationRecordService.listMemberInvitationRecordByQuery(query);
        int totalInvitationNum = (int)memberInvitationRecordService.countMemberInvitationRecordByMemberId(query.getMemberId());
        data.put("listPage", listPage);
        data.put("totalInvitationNum", totalInvitationNum);

        if (MemberInvitationRecordState.SUCCESS.equals(query.getState())) {
            data.put("validInvitationNum", listPage.getTotalItemsCount());
            data.put("noValidInvitationNum", totalInvitationNum - listPage.getTotalItemsCount());
        } else {
            data.put("validInvitationNum", totalInvitationNum - listPage.getTotalItemsCount());
            data.put("noValidInvitationNum", listPage.getTotalItemsCount());
        }

        return CommonVOUtil.success(data, "success");
    }

    @RequestMapping("/updateInvitationState")
    public CommonVO updateInvitationState(String invitationMemberId) {
        if (StringUtils.isBlank(invitationMemberId)) {
            return CommonVOUtil.lackParameter();
        }

        MemberInvitationRecord record = memberInvitationRecordService.findMemberInvitationRecordByInvitationMemberId(invitationMemberId);
        if (record == null) {
            return CommonVOUtil.error("parameter error");
        }
        memberInvitationRecordService.updateMemberInvitationRecordState(record.getId(), MemberInvitationRecordState.SUCCESS);
        adminHongbaoAdjustMsgService.updateInvitationState(invitationMemberId);
        return CommonVOUtil.success("success");
    }
}
