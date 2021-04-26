package com.anbang.qipai.admin.msg.receiver.memberreceiver;

import com.anbang.qipai.admin.msg.channel.sink.MemberScoresSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.members.MemberScoreRecordDbo;
import com.anbang.qipai.admin.plan.service.membersservice.MemberDboService;
import com.anbang.qipai.admin.plan.service.membersservice.MemberScoreService;
import com.dml.accounting.AccountingSummary;
import com.dml.accounting.TextAccountingSummary;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Map;

@EnableBinding(MemberScoresSink.class)
public class MemberScoresMsgReceiver {
    @Autowired
    private MemberScoreService memberScoreService;

    @Autowired
    private MemberDboService memberService;

    private Gson gson = new Gson();

    @StreamListener(MemberScoresSink.MEMBERSCORES)
    public void recordMemberScoreRecordDbo(CommonMO mo) {
        Map<String, Object> map = (Map<String, Object>) mo.getData();
        try {
            if ("accounting".equals(mo.getMsg())) {
                long no = Long.valueOf((int) map.get("accountingNo"));
                String memberId = (String) map.get("memberId");
                MemberScoreRecordDbo recentDbo = memberScoreService.findRecentlyScoreRecordByMemberId(memberId);
                if (recentDbo == null || no > recentDbo.getAccountingNo()) {
                    MemberScoreRecordDbo dbo = new MemberScoreRecordDbo();
                    dbo.setId((String) map.get("id"));
                    dbo.setAccountId((String) map.get("accountId"));
                    dbo.setMemberId(memberId);
                    dbo.setAccountingNo(no);
                    dbo.setBalanceAfter((int) map.get("balanceAfter"));
                    AccountingSummary summary = new TextAccountingSummary(
                            (String) ((Map<String, Object>) map.get("summary")).get("text"));
                    dbo.setSummary(summary);
                    dbo.setAccountingTime((long) map.get("accountingTime"));
                    dbo.setAccountingAmount((int) map.get("accountingAmount"));
                    memberScoreService.addScoreRecord(dbo);
                    memberService.updateMemberScore(memberId, (int) map.get("balanceAfter"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
