package com.anbang.qipai.admin.msg.receiver.memberreceiver;

import com.anbang.qipai.admin.msg.channel.sink.MemberLoginLimitRecordSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.members.MemberLoginLimitRecord;
import com.anbang.qipai.admin.plan.service.membersservice.MemberLoginLimitRecordService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MemberLoginLimitRecordSink.class)
public class MemberLoginLimitRecordMsgReceiver {
    @Autowired
    private MemberLoginLimitRecordService memberLoginLimitRecordService;

    private Gson gson = new Gson();

    @StreamListener(MemberLoginLimitRecordSink.MEMBERLOGINLIMITRECORD)
    public void memberClubCard(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        try {
            if ("add record".equals(msg)) {
                MemberLoginLimitRecord record = gson.fromJson(json, MemberLoginLimitRecord.class);
                memberLoginLimitRecordService.save(record);
            }
            if ("delete records".equals(msg)) {
                String[] recordIds = gson.fromJson(json, String[].class);
                memberLoginLimitRecordService.updateMemberLoginLimitRecordEfficientById(recordIds, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
