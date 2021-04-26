package com.anbang.qipai.admin.plan.service.agentsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.agents.AgentType;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentDboDao;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentTypeDao;
import com.highto.framework.web.page.ListPage;

@Service
public class AgentTypeService {

	@Autowired
	private AgentTypeDao agentTypeDao;

	@Autowired
	private AgentDboDao agentDboDao;

	public void save(AgentType type) {
		agentTypeDao.save(type);
	}

	public void removeByIds(String[] typeIds) {
		agentTypeDao.removeByIds(typeIds);
	}

	public void updateAgentType(AgentType type) {
		agentTypeDao.updateAgentType(type);
		agentDboDao.updateAgentTypeForTypeChange(type);
	}

	public ListPage findByConditions(int page, int size, AgentType type) {
		long amount = agentTypeDao.countAmountByConditions(type);
		List<AgentType> typeList = agentTypeDao.findByConditions(page, size, type);
		for (AgentType t : typeList) {
			t.setJuniorReward(t.getJuniorReward() * 100);
			t.setMemberReward(t.getMemberReward() * 100);
		}
		ListPage listPage = new ListPage(typeList, page, size, (int) amount);
		return listPage;
	}

	public AgentType findByType(String type) {
		return agentTypeDao.findByType(type);
	}

	public AgentType findById(String typeId) {
		return agentTypeDao.findById(typeId);
	}
}
