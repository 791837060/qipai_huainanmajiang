package com.anbang.qipai.admin.web.controller;

import com.anbang.qipai.admin.plan.bean.members.MemberDbo;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentMemberDayCostService;
import com.anbang.qipai.admin.plan.service.membersservice.MemberDboService;
import com.anbang.qipai.admin.util.CommonVOUtil;
import com.anbang.qipai.admin.web.query.AgentMemberCostQuery;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代理下辖玩家消费统计
 */
@CrossOrigin
@RestController
@RequestMapping("/agentMemberCost")
public class AgentMemberCostController {
    @Autowired
    private AgentMemberDayCostService agentMemberDayCostService;

    @Autowired
    private MemberDboService memberDboService;

    /**
     * 代理下辖玩家日玉石总消耗
     */
    @RequestMapping("/listAgentMemberCost")
    public CommonVO listAgentMemberCost(AgentMemberCostQuery agentMemberCostQuery, int page, int size) {
        ListPage listPage = agentMemberDayCostService.listByQuery(agentMemberCostQuery, page, size);
        return CommonVOUtil.success(listPage, "success");
    }

    /**
     * 代理下辖各玩家玉石消耗
     */
    @RequestMapping("/listMemberCost")
    public CommonVO listMemberCost(int page, int size, String agentId ,String goldTotalCostSort) {
        ListPage listPage = memberDboService.listMemberByAgentId(page, size, agentId, goldTotalCostSort);
        return CommonVOUtil.success(listPage, "success");
    }

}
