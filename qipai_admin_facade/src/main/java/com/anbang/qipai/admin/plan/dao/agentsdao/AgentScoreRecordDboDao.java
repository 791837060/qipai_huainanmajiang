package com.anbang.qipai.admin.plan.dao.agentsdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.agents.AgentScoreRecordDbo;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentScoreRecordDboVO;

public interface AgentScoreRecordDboDao {

	void addAgentScoreRecordDbo(AgentScoreRecordDbo record);

	long getAmountByConditions(AgentScoreRecordDboVO record);

	List<AgentScoreRecordDbo> findAgentScoreRecordDboByConditions(int page, int size, AgentScoreRecordDboVO record);
}
