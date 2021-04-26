package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.agents.AgentBackRewardRecordDbo;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentBackRewardRecordDboDao;

@Component
public class MongodbAgentBackRewardRecordDboDao implements AgentBackRewardRecordDboDao {

	@Autowired
	private MongoTemplate mognoTemplate;

	@Override
	public void save(AgentBackRewardRecordDbo dbo) {
		mognoTemplate.insert(dbo);
	}

}
