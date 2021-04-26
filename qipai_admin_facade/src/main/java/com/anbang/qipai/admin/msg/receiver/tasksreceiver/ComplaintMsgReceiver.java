package com.anbang.qipai.admin.msg.receiver.tasksreceiver;


import com.anbang.qipai.admin.msg.channel.sink.ComplaintSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.Complaint;

import com.anbang.qipai.admin.plan.service.ComplaintService;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(ComplaintSink.class)
public class ComplaintMsgReceiver {

    @Autowired
    private ComplaintService complaintService;

    private Gson gson = new Gson();

    @StreamListener(ComplaintSink.COMPLAINT)
    public void complaint(CommonMO mo) {
        String msg = mo.getMsg();
       // Complaint complaint = (Complaint)mo.getData();
        String json = gson.toJson(mo.getData());
        Complaint complaint = gson.fromJson(json, Complaint.class);
        try {
            if ("add complaint".equals(msg)) {
                complaintService.addComplaint(complaint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
