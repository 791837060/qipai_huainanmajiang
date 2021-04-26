package com.anbang.qipai.admin.plan.dao.agentsdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.agents.AgentApplyRecord;

public interface AgentApplyRecordDao {

	void addAgentApplyRecord(AgentApplyRecord record);

	void updateAgentApplyRecordSate(String recordId, String state);

	long getAmountByConditions(int page, int size, AgentApplyRecord record);

	List<AgentApplyRecord> findAgentApplyRecordByConditions(int page, int size, AgentApplyRecord record);

	AgentApplyRecord findAgentApplyRecordById(String recordId);
}
