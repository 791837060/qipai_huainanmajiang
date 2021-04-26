package com.anbang.qipai.admin.plan.dao.agentsdao;

import java.util.Map;

import com.anbang.qipai.admin.plan.bean.agents.AgentRewardOrder;

public interface AgentRewardOrderDao {
	void save(AgentRewardOrder order);

	AgentRewardOrder findById(String id);

	void updateReturnParams(String id, Map returnParams);
}
