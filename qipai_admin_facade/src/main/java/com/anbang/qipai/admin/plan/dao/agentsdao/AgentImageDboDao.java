package com.anbang.qipai.admin.plan.dao.agentsdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.agents.AgentImageDbo;

public interface AgentImageDboDao {

	void addAgentImageDbo(AgentImageDbo image);

	List<AgentImageDbo> findAgentImageDbo();

	AgentImageDbo findAgentImageDboById(String imageId);

	void deleteAgentImageDboById(String imageId);

	void deleteAgentImageDboByOrdinal(int ordinal);
}
