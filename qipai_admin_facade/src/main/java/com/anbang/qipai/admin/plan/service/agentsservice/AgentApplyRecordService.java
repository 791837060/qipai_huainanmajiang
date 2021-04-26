package com.anbang.qipai.admin.plan.service.agentsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.agents.AgentApplyRecord;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentApplyRecordDao;
import com.highto.framework.web.page.ListPage;

@Service
public class AgentApplyRecordService {

	@Autowired
	private AgentApplyRecordDao agentApplyRecordDao;

	public void addAgentApplyRecord(AgentApplyRecord record) {
		agentApplyRecordDao.addAgentApplyRecord(record);
	}

	public void updateAgentApplyRecordSate(String recordId, String state) {
		agentApplyRecordDao.updateAgentApplyRecordSate(recordId, state);
	}

	public ListPage findAgentApplyRecordByConditions(int page, int size, AgentApplyRecord record) {
		long amount = agentApplyRecordDao.getAmountByConditions(page, size, record);
		List<AgentApplyRecord> recordList = agentApplyRecordDao.findAgentApplyRecordByConditions(page, size, record);
		ListPage listPage = new ListPage(recordList, page, size, (int) amount);
		return listPage;
	}

	public AgentApplyRecord findAgentApplyRecordById(String recordId) {
		return agentApplyRecordDao.findAgentApplyRecordById(recordId);
	}
}
