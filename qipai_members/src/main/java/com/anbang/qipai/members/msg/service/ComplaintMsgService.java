package com.anbang.qipai.members.msg.service;


import com.anbang.qipai.members.msg.channel.source.ComplaintSource;
import com.anbang.qipai.members.msg.msjobj.CommonMO;
import com.anbang.qipai.members.plan.bean.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(ComplaintSource.class)
public class ComplaintMsgService {

    @Autowired
    private ComplaintSource complaintSource;

    public void addComplaint(Complaint complaint) {
        CommonMO mo = new CommonMO();
        mo.setMsg("add complaint");
        mo.setData(complaint);

        complaintSource.complaint().send(MessageBuilder.withPayload(mo).build());
    }
}
