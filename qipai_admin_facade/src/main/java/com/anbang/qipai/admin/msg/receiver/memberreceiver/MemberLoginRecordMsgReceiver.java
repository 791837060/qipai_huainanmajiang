package com.anbang.qipai.admin.msg.receiver.memberreceiver;

import com.anbang.qipai.admin.msg.channel.sink.MemberLoginRecordSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.members.MemberDbo;
import com.anbang.qipai.admin.plan.bean.members.MemberLoginRecord;
import com.anbang.qipai.admin.plan.bean.report.OnlineStateRecord;
import com.anbang.qipai.admin.plan.service.membersservice.MemberDboService;
import com.anbang.qipai.admin.plan.service.membersservice.MemberLoginRecordService;
import com.anbang.qipai.admin.publisher.service.OnlineRecordService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Map;

@EnableBinding(MemberLoginRecordSink.class)
public class MemberLoginRecordMsgReceiver {

    @Autowired
    private MemberLoginRecordService memberLoginRecordService;

    @Autowired
    private MemberDboService memberDboService;

    @Autowired
    private OnlineRecordService onlineRecordService;

    private Gson gson = new Gson();

    @StreamListener(MemberLoginRecordSink.MEMBERLOGINRECORD)
    public void memberLoginRecord(CommonMO mo) {
        String msg = mo.getMsg();
        Map map = (Map) mo.getData();
        try {
            if ("member login".equals(msg)) {
                String json = gson.toJson(map.get("record"));
                MemberLoginRecord record = gson.fromJson(json, MemberLoginRecord.class);
                MemberDbo member = memberDboService.findMemberById(record.getMemberId());
                record.setNickname(member.getNickname());
                memberLoginRecordService.save(record);
                String onlineState = (String) map.get("onlineState");
                String loginIp = record.getLoginIp();
                String ipAddress = record.getIpAddress();
                memberDboService.updateOnlineStateAndIP(record.getMemberId(), onlineState, loginIp, ipAddress);

                // 利用SpringEvent添加上线记录
                OnlineStateRecord onlineStateRecord = new OnlineStateRecord(member.getId(), System.currentTimeMillis(),
                        OnlineStateRecord.ON_LINE);
                onlineRecordService.sendRecord(onlineStateRecord);
            }
            if ("update member onlineTime".equals(msg)) {
                String json = gson.toJson(mo.getData());
                MemberLoginRecord record = gson.fromJson(json, MemberLoginRecord.class);
                memberLoginRecordService.updateOnlineTimeById(record.getId(), record.getOnlineTime());
            }
            if ("member logout".equals(msg)) {
                String memberId = (String) map.get("memberId");
                String onlineState = (String) map.get("onlineState");
                memberDboService.updateMemberOnlineState(memberId, onlineState);

                // 利用SpringEvent添加下线记录
                OnlineStateRecord onlineStateRecord = new OnlineStateRecord(memberId, System.currentTimeMillis(),
                        OnlineStateRecord.OFF_LINE);
                onlineRecordService.sendRecord(onlineStateRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
