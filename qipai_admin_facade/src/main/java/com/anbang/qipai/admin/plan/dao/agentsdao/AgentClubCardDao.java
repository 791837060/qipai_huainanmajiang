package com.anbang.qipai.admin.plan.dao.agentsdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.agents.AgentClubCard;

public interface AgentClubCardDao {

	void addAgentClubCard(AgentClubCard card);

	void deleteAgentClubCard(String cardId);

	void updateAgentClubCard(AgentClubCard card);

	List<AgentClubCard> findAgentClubCardByConditions(int page, int size, AgentClubCard card);

	long getAmountByConditions(AgentClubCard card);

	AgentClubCard findAgentClubCardById(String id);
}
