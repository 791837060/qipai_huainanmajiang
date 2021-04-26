package com.anbang.qipai.admin.plan.dao.agentsdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.agents.AgentInvitationRecord;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentInvitationRecordVO;

public interface AgentInvitationRecordDao {

	void addInvitationRecord(AgentInvitationRecord record);

	void saveInvitationRecord(AgentInvitationRecord record);

	List<AgentInvitationRecord> findInvitationRecordByConditions(int page, int size, AgentInvitationRecordVO record);

	long getAmountByByConditions(AgentInvitationRecordVO record);

	void banInvitationRecordById(String id);
}
