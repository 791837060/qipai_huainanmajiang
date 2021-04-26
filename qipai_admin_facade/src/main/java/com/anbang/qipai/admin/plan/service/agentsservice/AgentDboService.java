package com.anbang.qipai.admin.plan.service.agentsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.agents.AgentDbo;
import com.anbang.qipai.admin.plan.bean.agents.AgentType;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentDboDao;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentDboVO;
import com.highto.framework.web.page.ListPage;

@Service
public class AgentDboService {

	@Autowired
	private AgentDboDao agentDboDao;

	public ListPage findAgentDboByConditions(int page, int size, AgentDboVO agent) {
		long amount = agentDboDao.getAmountByConditions(agent);
		List<AgentDbo> agentList = agentDboDao.findAgentDboByConditions(page, size, agent);
		for (AgentDbo agentDbo : agentList) {
			AgentType type = agentDbo.getAgentType();
			if (type != null) {
				type.setJuniorReward(type.getJuniorReward() * 100);
				type.setMemberReward(type.getMemberReward() * 100);
			}
		}
		ListPage listPage = new ListPage(agentList, page, size, (int) amount);
		return listPage;
	}

	public long countAmountByConditions(AgentDboVO agent) {
		return agentDboDao.getAmountByConditions(agent);
	}

	public AgentDbo findAgentDboById(String agentId) {
		return agentDboDao.findAgentDboById(agentId);
	}

	public void updateAgentCost(String agentId, double cost) {
		agentDboDao.updateAgentCost(agentId, cost);
	}

	public void addAgentDbo(AgentDbo agent) {
		agentDboDao.addAgentDbo(agent);
	}

	public void updateAgentDboBoss(String agentId, String bossId, String bossName) {
		agentDboDao.updateAgentDboBoss(agentId, bossId, bossName);
	}

	public void removeAgentDboBoss(String agentId) {
		agentDboDao.removeAgentDboBoss(agentId);
	}

	public void updateAgentDboState(String agentId, String state) {
		agentDboDao.updateAgentDboState(agentId, state);
	}

	public void updateAgentDboType(String agentId, AgentType type) {
		agentDboDao.updateAgentDboType(agentId, type);
	}

	public void updateAgnetInfoAndAgentAuth(AgentDbo agent) {
		agentDboDao.updateAgentDboInvitationCode(agent.getId(), agent.getInvitationCode());
		agentDboDao.updateAgnetInfo(agent.getId(), agent.getPhone(), agent.getUserName(), agent.getArea(),
				agent.getDesc());
		agentDboDao.updateAgentAuth(agent.getId(), agent.isAgentAuth());
	}

	public void updateAgentInviteMemberNum(String agentId, int inviteMemberNum) {
		agentDboDao.updateAgentInviteMemberNum(agentId, inviteMemberNum);
	}

	public void updateAgentJuniorNum(String agentId, int juniorNum) {
		agentDboDao.updateAgentJuniorNum(agentId, juniorNum);
	}
}
