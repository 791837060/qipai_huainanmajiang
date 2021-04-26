package com.anbang.qipai.dalianmeng.msg.receiver;

import com.anbang.qipai.dalianmeng.cqrs.q.service.LianmengService;
import com.anbang.qipai.dalianmeng.msg.msjobs.CommonMO;
import com.anbang.qipai.dalianmeng.msg.sink.MemberLoginRecordSink;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Map;

@EnableBinding(MemberLoginRecordSink.class)
public class MemberLoginRecordMsgReceiver {
    @Autowired
    private LianmengService lianmengService;

    private Gson gson = new Gson();

    @StreamListener(MemberLoginRecordSink.MEMBERLOGINRECORD)
    public void memberLoginRecord(CommonMO mo) {
        String msg = mo.getMsg();
        Map map = (Map) mo.getData();
        if ("member login".equals(msg)) {
            Map record = (Map)map.get("record");
            String memberId = (String) record.get("memberId");
            String onlineState = (String) map.get("onlineState");
            lianmengService.updateMemberLianmengDboOnlineState(memberId, onlineState);
        } else if ("update member onlineTime".equals(msg)) {
            String memberId = (String) map.get("memberId");
            lianmengService.updateMemberLianmengDboOnlineState(memberId, "online");

        } else if ("member logout".equals(msg)) {
            String memberId = (String) map.get("memberId");
            String onlineState = (String) map.get("onlineState");
            lianmengService.updateMemberLianmengDboOnlineState(memberId, onlineState);
        }
    }
}
