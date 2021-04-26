package com.anbang.qipai.admin.plan.service.agentsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.agents.AgentClubCard;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentClubCardDao;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentPayType;
import com.highto.framework.web.page.ListPage;

@Service
public class AgentClubCardService {

	@Autowired
	private AgentClubCardDao clubCardDao;

	public ListPage findAgentClubCardByConditions(int page, int size, AgentClubCard card) {
		long amount = clubCardDao.getAmountByConditions(card);
		List<AgentClubCard> cardList = clubCardDao.findAgentClubCardByConditions(page, size, card);
		for (int i = 0; i < cardList.size(); i++) {
			String payType = cardList.get(i).getPayType();
			cardList.get(i).setPayType(AgentPayType.getSummaryText(payType));
		}
		ListPage listPage = new ListPage(cardList, page, size, (int) amount);
		return listPage;
	}

	public void addAgentClubCard(AgentClubCard card) {
		clubCardDao.addAgentClubCard(card);
	}

	public void updateAgentClubCard(AgentClubCard card) {
		clubCardDao.updateAgentClubCard(card);
	}

	public void deleteAgentClubCard(String cardId) {
		clubCardDao.deleteAgentClubCard(cardId);
	}

	public AgentClubCard findAgentClubCardById(String id) {
		return clubCardDao.findAgentClubCardById(id);
	}

	public int queryProductNum (String id) {
		int number = 0;
		AgentClubCard agentClubCard = clubCardDao.findAgentClubCardById(id);
		if (agentClubCard != null) {
			number = agentClubCard.getNumber();
		}
		return number;
	}
}
