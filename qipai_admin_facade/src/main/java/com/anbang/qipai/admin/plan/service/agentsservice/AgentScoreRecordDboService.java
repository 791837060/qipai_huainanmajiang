package com.anbang.qipai.admin.plan.service.agentsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.agents.AgentScoreRecordDbo;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentScoreRecordDboDao;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentScoreRecordDboVO;
import com.anbang.qipai.admin.web.vo.agentsvo.RecordSummaryTexts;
import com.dml.accounting.TextAccountingSummary;
import com.highto.framework.web.page.ListPage;

@Service
public class AgentScoreRecordDboService {

	@Autowired
	private AgentScoreRecordDboDao agentScoreRecordDboDao;

	public void addAgentScoreRecordDbo(AgentScoreRecordDbo dbo) {
		agentScoreRecordDboDao.addAgentScoreRecordDbo(dbo);
	}

	public ListPage findAgentScoreRecordDboByConditions(int page, int size, AgentScoreRecordDboVO record) {
		long amount = agentScoreRecordDboDao.getAmountByConditions(record);
		List<AgentScoreRecordDbo> recordList = agentScoreRecordDboDao.findAgentScoreRecordDboByConditions(page, size,
				record);
		for (int i = 0; i < recordList.size(); i++) {
			TextAccountingSummary summary = (TextAccountingSummary) recordList.get(i).getSummary();
			TextAccountingSummary newSummary = new TextAccountingSummary(
					RecordSummaryTexts.getSummaryText(summary.getText()));
			recordList.get(i).setSummary(newSummary);
		}
		ListPage listPage = new ListPage(recordList, page, size, (int) amount);
		return listPage;
	}
}
