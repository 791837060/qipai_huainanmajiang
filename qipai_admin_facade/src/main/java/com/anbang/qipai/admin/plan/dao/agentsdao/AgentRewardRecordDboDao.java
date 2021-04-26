package com.anbang.qipai.admin.plan.dao.agentsdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.agents.AgentRewardRecordDbo;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentRewardRecordDboVO;

public interface AgentRewardRecordDboDao {
	void addAgentRewardRecordDbo(AgentRewardRecordDbo record);

	long getAmountByConditions(AgentRewardRecordDboVO record);

	List<AgentRewardRecordDbo> findAgentRewardRecordDboByConditions(int page, int size, AgentRewardRecordDboVO record);

	double findAmountByConditions(AgentRewardRecordDboVO record);
}
