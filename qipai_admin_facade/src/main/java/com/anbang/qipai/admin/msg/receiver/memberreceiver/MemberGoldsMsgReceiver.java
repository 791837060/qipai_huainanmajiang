package com.anbang.qipai.admin.msg.receiver.memberreceiver;

import com.anbang.qipai.admin.msg.channel.sink.MemberGoldsSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.agents.AgentDbo;
import com.anbang.qipai.admin.plan.bean.agents.AgentMemberDayCost;
import com.anbang.qipai.admin.plan.bean.members.MemberDbo;
import com.anbang.qipai.admin.plan.bean.members.MemberGoldRecordDbo;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentDboService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentMemberDayCostService;
import com.anbang.qipai.admin.plan.service.membersservice.MemberDboService;
import com.anbang.qipai.admin.plan.service.membersservice.MemberGoldService;
import com.anbang.qipai.admin.util.TimeUtil;
import com.dml.accounting.AccountingSummary;
import com.dml.accounting.TextAccountingSummary;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Map;

@EnableBinding(MemberGoldsSink.class)
public class MemberGoldsMsgReceiver {

    @Autowired
    private MemberGoldService memberGoldService;

    @Autowired
    private MemberDboService memberService;

    @Autowired
    private AgentDboService agentDboService;

    @Autowired
    private AgentMemberDayCostService agentMemberDayCostService;

    @StreamListener(MemberGoldsSink.MEMBERGOLDS)
    public void recordMemberGoldRecordDbo(CommonMO mo) {
        Map<String, Object> map = (Map<String, Object>) mo.getData();
        try {
            if ("accounting".equals(mo.getMsg())) {
                long no = Long.valueOf((int) map.get("accountingNo"));
                String memberId = (String) map.get("memberId");
                MemberGoldRecordDbo recentDbo = memberGoldService.findRecentlyGoldRecordByMemberId(memberId);
                if (recentDbo == null || no > recentDbo.getAccountingNo()) {
                    MemberGoldRecordDbo dbo = new MemberGoldRecordDbo();
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
                    int goldTotalCost = goldTotalCost(memberId, dbo);
                    memberGoldService.addGoldRecord(dbo);
                    memberService.updateMemberGoldAndCost(memberId, (int) map.get("balanceAfter"), goldTotalCost);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 统计玩家玉石总消费
     * @return goldTotalCost
     */
    private int goldTotalCost(String memberId, MemberGoldRecordDbo dbo) {
        MemberDbo memberDbo = memberService.findMemberById(memberId);
        if (memberDbo == null) {
            return 0;
        }

        // 统计消耗
        if (dbo.getAccountingAmount() < 0) {
            int cost =  - dbo.getAccountingAmount();
            int goldTotalCost = memberDbo.getGoldTotalCost() + cost;
            if (memberDbo.isBindAgent()) {
                String agentId = memberDbo.getAgentId();
                long nowTime = System.currentTimeMillis();
                String id = TimeUtil.getNowDayStr(nowTime) + "-" + agentId;
                AgentMemberDayCost agentMemberDayCost = agentMemberDayCostService.getById(id);
                if (agentMemberDayCost == null) {
                    AgentDbo agentDbo = agentDboService.findAgentDboById(agentId);
                    agentMemberDayCost = new AgentMemberDayCost();
                    agentMemberDayCost.setId(id);
                    agentMemberDayCost.setAgentId(agentId);
                    agentMemberDayCost.setNickName(agentDbo.getNickname());
                    agentMemberDayCost.setType("gold");
                    agentMemberDayCost.setDayTotalCost(cost);
                    agentMemberDayCost.setCreateTime(nowTime);
                    agentMemberDayCostService.save(agentMemberDayCost);
                } else {
                    agentMemberDayCostService.updateDayTotalCost(id, agentMemberDayCost.getDayTotalCost() + cost);
                }
            }
            return goldTotalCost;
        }
        return memberDbo.getGoldTotalCost();
    }
}
