package com.anbang.qipai.admin.plan.service.agentsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.agents.AgentClubCardRecordDbo;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentClubCardRecordDboDao;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentClubCardRecordDboVO;
import com.anbang.qipai.admin.web.vo.agentsvo.RecordSummaryTexts;
import com.dml.accounting.TextAccountingSummary;
import com.highto.framework.web.page.ListPage;

@Service
public class AgentClubCardRecordDboService {

	@Autowired
	private AgentClubCardRecordDboDao agentClubCardRecordDboDao;

	public void addAgentClubCardRecordDbo(AgentClubCardRecordDbo dbo) {
		agentClubCardRecordDboDao.addAgentClubCardRecordDbo(dbo);
	}

	public ListPage findAgentClubCardRecordDboByConditions(int page, int size, AgentClubCardRecordDboVO record) {
		long amount = agentClubCardRecordDboDao.getAmountByConditions(record);
		List<AgentClubCardRecordDbo> recordList = agentClubCardRecordDboDao.findAgentClubCardRecordDboByConditions(page,
				size, record);
		for (int i = 0; i < recordList.size(); i++) {
			TextAccountingSummary summary = (TextAccountingSummary) recordList.get(i).getSummary();
			TextAccountingSummary newSummary = new TextAccountingSummary(
					RecordSummaryTexts.getSummaryText(summary.getText()));
			recordList.get(i).setSummary(newSummary);
		}
		ListPage listPage = new ListPage(recordList, page, size, (int) amount);
		return listPage;
	}

	public int countProductNumByTimeAndProduct(String productName, String type, long startTime, long endTime) {
		return agentClubCardRecordDboDao.countProductNumByTimeAndProduct(productName, type, startTime, endTime);
	}
}
