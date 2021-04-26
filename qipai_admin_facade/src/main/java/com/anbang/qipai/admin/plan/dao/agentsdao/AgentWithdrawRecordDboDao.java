package com.anbang.qipai.admin.plan.dao.agentsdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.agents.AgentWithdrawRecordDbo;

public interface AgentWithdrawRecordDboDao {

	void save(AgentWithdrawRecordDbo dbo);

	long countAmountByConditions(AgentWithdrawRecordDbo dbo);

	List<AgentWithdrawRecordDbo> findByConditions(int page, int size, AgentWithdrawRecordDbo dbo);

	AgentWithdrawRecordDbo findById(String id);

	void updateAgentWithdrawRecordDboState(String id, String state);

	void updateAgentWithdrawRecordDboDescAndState(String id, String state, String desc);
}
