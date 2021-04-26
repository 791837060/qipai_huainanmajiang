package com.anbang.qipai.admin.plan.service.chaguanservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanYushiRecordDbo;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentDboDao;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanYushiRecordDboDao;
import com.anbang.qipai.admin.web.vo.chaguanvo.ChaguanYushiRecordDboVO;
import com.anbang.qipai.admin.web.vo.chaguanvo.ChaguanYushiRecordVO;
import com.highto.framework.web.page.ListPage;

@Service
public class ChaguanYushiService {

	@Autowired
	private ChaguanYushiRecordDboDao chaguanYushiRecordDboDao;

	@Autowired
	private AgentDboDao agentDboDao;

	public ListPage findRecordByConditions(int page, int size, ChaguanYushiRecordDboVO record,String text) {
		long amount = chaguanYushiRecordDboDao.countByConditions(record,text);
		List<ChaguanYushiRecordVO> list = new ArrayList<>();
		List<ChaguanYushiRecordDbo> recordList = chaguanYushiRecordDboDao.findByConditions(page, size, record,text);
		for (ChaguanYushiRecordDbo dbo : recordList) {
			ChaguanYushiRecordVO vo = new ChaguanYushiRecordVO();
			vo.setAccountId(dbo.getAccountId());
			vo.setAccountingAmount(dbo.getAccountingAmount());
			vo.setAccountingTime(dbo.getAccountingTime());
			vo.setAgentId(dbo.getAgentId());
			vo.setBalanceAfter(dbo.getBalanceAfter());
			vo.setId(dbo.getId());
			vo.setNickname(agentDboDao.findAgentDboById(dbo.getAgentId()).getNickname());
			vo.setSummary(dbo.getSummary());
			list.add(vo);
		}
		ListPage listPage = new ListPage(list, page, size, (int) amount);
		return listPage;
	}

	public void recordChaguanYushiRecordDbo(ChaguanYushiRecordDbo dbo) {
		chaguanYushiRecordDboDao.save(dbo);
	}
}
