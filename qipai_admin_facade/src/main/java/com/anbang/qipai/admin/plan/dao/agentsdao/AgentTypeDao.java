package com.anbang.qipai.admin.plan.dao.agentsdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.agents.AgentType;

public interface AgentTypeDao {

	void save(AgentType type);

	void removeByIds(String[] typeIds);

	void updateAgentType(AgentType type);

	long countAmountByConditions(AgentType type);

	List<AgentType> findByConditions(int page, int size, AgentType type);

	AgentType findById(String typeId);

	AgentType findByType(String type);
}
