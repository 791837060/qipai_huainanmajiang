package com.anbang.qipai.admin.plan.service.agentsservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.agents.AgentImageDbo;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentImageDboDao;

@Service
public class AgentImageDboService {

	@Autowired
	private AgentImageDboDao agentImageDboDao;

	public void addAgentImageDbo(AgentImageDbo image) {
		agentImageDboDao.addAgentImageDbo(image);
	}

	public List<AgentImageDbo> findAgentImageDbo() {
		return agentImageDboDao.findAgentImageDbo();
	}

	public AgentImageDbo findAgentImageDboById(String imageId) {
		return agentImageDboDao.findAgentImageDboById(imageId);
	}

	public void deleteAgentImageDboById(String imageId) {
		agentImageDboDao.deleteAgentImageDboById(imageId);
	}

	public void deleteAgentImageDboByOrdinal(int ordinal) {
		agentImageDboDao.deleteAgentImageDboByOrdinal(ordinal);
	}
}
