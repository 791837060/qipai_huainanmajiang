package com.anbang.qipai.members.web.controller;


import com.anbang.qipai.members.cqrs.c.service.MemberAuthService;
import com.anbang.qipai.members.plan.bean.Complaint;
import com.anbang.qipai.members.plan.bean.MemberLoginLimitRecord;
import com.anbang.qipai.members.plan.service.ComplaintService;
import com.anbang.qipai.members.plan.service.MemberLoginLimitRecordService;
import com.anbang.qipai.members.web.vo.CommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private MemberLoginLimitRecordService memberLoginLimitRecordService;

    @Autowired
    private ComplaintService complaintService;


    @RequestMapping("/memberComplaint")
    public CommonVO memberComplaint(String token, String complaintText){
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }
        if (complaintText==null){
            vo.setSuccess(false);
            vo.setMsg("请输入投诉内容");
            return vo;
        }else {
            Complaint complaint=new Complaint();
            complaint.setMemberId(memberId);
            complaint.setComplaintText(complaintText);
            complaintService.save(complaint);
            vo.setMsg("投诉成功");
            return vo;
        }
    }

}
