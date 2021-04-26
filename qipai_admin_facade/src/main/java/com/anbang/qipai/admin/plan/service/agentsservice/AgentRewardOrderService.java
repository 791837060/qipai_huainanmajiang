package com.anbang.qipai.admin.plan.service.agentsservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.agents.AgentRewardOrder;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentRewardOrderDao;

@Service
public class AgentRewardOrderService {

	@Autowired
	private AgentRewardOrderDao agentRewardOrderDao;

	public void save(AgentRewardOrder order) {
		agentRewardOrderDao.save(order);
	}

	public AgentRewardOrder findById(String id) {
		return agentRewardOrderDao.findById(id);
	}

	public void updateReturnParams(String id, Map returnParams) {
		agentRewardOrderDao.updateReturnParams(id, returnParams);
	}
}
