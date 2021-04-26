package com.anbang.qipai.admin.plan.dao.agentsdao;

import com.anbang.qipai.admin.plan.bean.agents.AgentMemberDayCost;
import com.anbang.qipai.admin.web.query.AgentMemberCostQuery;

import java.util.List;

public interface AgentMemberDayCostDao {
    void save(AgentMemberDayCost agentMemberDayCost);

    void updateDayTotalCost(String id, int dayTotalCost);

    AgentMemberDayCost get(String id);

    int countByQuery(AgentMemberCostQuery costQuery);

    List<AgentMemberDayCost> listByQuery(AgentMemberCostQuery costQuery, int page, int size);
}
