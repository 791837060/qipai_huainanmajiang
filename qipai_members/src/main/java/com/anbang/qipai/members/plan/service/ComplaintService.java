package com.anbang.qipai.members.plan.service;


import com.anbang.qipai.members.cqrs.q.dao.MemberDboDao;
import com.anbang.qipai.members.cqrs.q.dbo.MemberDbo;
import com.anbang.qipai.members.msg.service.ComplaintMsgService;
import com.anbang.qipai.members.plan.bean.Complaint;
import com.anbang.qipai.members.plan.dao.ComplaintDao;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ComplaintService {

    @Autowired
    private ComplaintDao complaintDao;

    @Autowired
    private MemberDboDao memberDboDao;

    @Autowired
    private ComplaintMsgService complaintMsgService;

    public void save(Complaint complaint) {
        String memberId = complaint.getMemberId();
        MemberDbo memberDbo = memberDboDao.findMemberById(memberId);
        complaint.setNickname(memberDbo.getNickname());
        complaint.setComplaintTime(System.currentTimeMillis());
        complaintDao.save(complaint);
        complaintMsgService.addComplaint(complaint);
    }



}
