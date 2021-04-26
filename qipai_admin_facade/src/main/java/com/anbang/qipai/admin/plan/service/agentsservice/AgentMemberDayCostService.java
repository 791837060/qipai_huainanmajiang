package com.anbang.qipai.admin.plan.service.agentsservice;

import com.anbang.qipai.admin.plan.bean.agents.AgentMemberDayCost;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentMemberDayCostDao;
import com.anbang.qipai.admin.web.query.AgentMemberCostQuery;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentMemberDayCostService {
    @Autowired
    private AgentMemberDayCostDao agentMemberDayCostDao;

    public void save(AgentMemberDayCost agentMemberDayCost){
        agentMemberDayCostDao.save(agentMemberDayCost);
    }

    public void updateDayTotalCost(String id, int dayTotalCost){
        agentMemberDayCostDao.updateDayTotalCost(id, dayTotalCost);
    }

    public AgentMemberDayCost getById(String id) {
        return agentMemberDayCostDao.get(id);
    }

    public ListPage listByQuery(AgentMemberCostQuery costQuery, int page, int size){
        int count = agentMemberDayCostDao.countByQuery(costQuery);
        List<AgentMemberDayCost> agentMemberDayCosts = agentMemberDayCostDao.listByQuery(costQuery, page, size);
        return new ListPage(agentMemberDayCosts, page, size, count);
    }
}
