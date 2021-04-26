package com.anbang.qipai.admin.plan.dao.mongodb.mongodbagentsdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.agents.AgentDbo;
import com.anbang.qipai.admin.plan.bean.agents.AgentType;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentDboDao;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentDboVO;

@Component
public class MongodbAgentDboDao implements AgentDboDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addAgentDbo(AgentDbo agent) {
		mongoTemplate.insert(agent);
	}

	@Override
	public List<AgentDbo> findAgentDboByConditions(int page, int size, AgentDboVO agent) {
		Query query = new Query();
		if (agent.getId() != null && !"".equals(agent.getId())) {
			query.addCriteria(Criteria.where("id").is(agent.getId()));
		}
		if (agent.getNickname() != null && !"".equals(agent.getNickname())) {
			query.addCriteria(Criteria.where("nickname").regex(agent.getNickname()));
		}
		if (agent.getBossId() != null && !"".equals(agent.getBossId())) {
			query.addCriteria(Criteria.where("bossId").is(agent.getBossId()));
		}
		if (agent.getPhone() != null && !"".equals(agent.getPhone())) {
			query.addCriteria(Criteria.where("phone").regex(agent.getPhone()));
		}
		if (agent.getUserName() != null && !"".equals(agent.getUserName())) {
			query.addCriteria(Criteria.where("userName").regex(agent.getUserName()));
		}
		if (agent.getType() != null && !"".equals(agent.getType())) {
			query.addCriteria(Criteria.where("agentType.type").regex(agent.getType()));
		}
		if (agent.getStartTime() != null || agent.getEndTime() != null) {
			Criteria criteria = Criteria.where("createTime");
			if (agent.getStartTime() != null) {
				criteria = criteria.gte(agent.getStartTime());
			}
			if (agent.getEndTime() != null) {
				criteria = criteria.lte(agent.getEndTime());
			}
			query.addCriteria(criteria);
		}
		query.addCriteria(Criteria.where("agentAuth").is(true));
		query.with(agent.getSort());
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, AgentDbo.class);
	}

	@Override
	public long getAmountByConditions(AgentDboVO agent) {
		Query query = new Query();
		if (agent.getId() != null && !"".equals(agent.getId())) {
			query.addCriteria(Criteria.where("id").is(agent.getId()));
		}
		if (agent.getNickname() != null && !"".equals(agent.getNickname())) {
			query.addCriteria(Criteria.where("nickname").regex(agent.getNickname()));
		}
		if (agent.getBossId() != null && !"".equals(agent.getBossId())) {
			query.addCriteria(Criteria.where("bossId").is(agent.getBossId()));
		}
		if (agent.getPhone() != null && !"".equals(agent.getPhone())) {
			query.addCriteria(Criteria.where("phone").regex(agent.getPhone()));
		}
		if (agent.getUserName() != null && !"".equals(agent.getUserName())) {
			query.addCriteria(Criteria.where("userName").regex(agent.getUserName()));
		}
		if (agent.getType() != null && !"".equals(agent.getType())) {
			query.addCriteria(Criteria.where("agentType.type").regex(agent.getType()));
		}
		if (agent.getStartTime() != null || agent.getEndTime() != null) {
			Criteria criteria = Criteria.where("createTime");
			if (agent.getStartTime() != null) {
				criteria = criteria.gte(agent.getStartTime());
			}
			if (agent.getEndTime() != null) {
				criteria = criteria.lte(agent.getEndTime());
			}
			query.addCriteria(criteria);
		}
		query.addCriteria(Criteria.where("agentAuth").is(true));
		return mongoTemplate.count(query, AgentDbo.class);
	}

	@Override
	public void updateAgentDboBoss(String agentId, String bossId, String bossName) {
		Query query = new Query(Criteria.where("id").is(agentId));
		Update update = new Update();
		update.set("bossId", bossId);
		update.set("bossName", bossName);
		mongoTemplate.updateFirst(query, update, AgentDbo.class);
	}

	@Override
	public void updateAgentDboType(String agentId, AgentType type) {
		Query query = new Query(Criteria.where("id").is(agentId));
		Update update = new Update();
		update.set("agentType", type);
		mongoTemplate.updateFirst(query, update, AgentDbo.class);
	}

	@Override
	public void updateAgnetInfo(String agentId, String phone, String userName, String area, String desc) {
		Query query = new Query(Criteria.where("id").is(agentId));
		Update update = new Update();
		update.set("phone", phone);
		update.set("userName", userName);
		update.set("area", area);
		update.set("desc", desc);
		mongoTemplate.updateFirst(query, update, AgentDbo.class);
	}

	@Override
	public void updateAgentAuth(String agentId, boolean agentAuth) {
		Query query = new Query(Criteria.where("id").is(agentId));
		Update update = new Update();
		update.set("agentAuth", agentAuth);
		mongoTemplate.updateFirst(query, update, AgentDbo.class);
	}

	@Override
	public void updateAgentDboState(String agentId, String state) {
		Query query = new Query(Criteria.where("id").is(agentId));
		Update update = new Update();
		update.set("state", state);
		mongoTemplate.updateFirst(query, update, AgentDbo.class);
	}

	@Override
	public AgentDbo findAgentDboById(String agentId) {
		Query query = new Query(Criteria.where("id").is(agentId));
		return mongoTemplate.findOne(query, AgentDbo.class);
	}

	@Override
	public void removeAgentDboBoss(String agentId) {
		Query query = new Query(Criteria.where("id").is(agentId));
		Update update = new Update();
		update.unset("bossId");
		update.unset("bossName");
		mongoTemplate.updateFirst(query, update, AgentDbo.class);
	}

	@Override
	public void updateAgentCost(String agentId, double cost) {
		Query query = new Query(Criteria.where("id").is(agentId));
		Update update = new Update();
		update.set("cost", cost);
		mongoTemplate.updateFirst(query, update, AgentDbo.class);
	}

	@Override
	public void updateAgentDboInvitationCode(String agentId, String invitationCode) {
		Query query = new Query(Criteria.where("id").is(agentId));
		Update update = new Update();
		update.set("invitationCode", invitationCode);
		mongoTemplate.updateFirst(query, update, AgentDbo.class);
	}

	@Override
	public void updateAgentInviteMemberNum(String agentId, int inviteMemberNum) {
		Query query = new Query(Criteria.where("id").is(agentId));
		Update update = new Update();
		update.set("inviteMemberNum", inviteMemberNum);
		mongoTemplate.updateFirst(query, update, AgentDbo.class);
	}

	@Override
	public void updateAgentJuniorNum(String agentId, int juniorNum) {
		Query query = new Query(Criteria.where("id").is(agentId));
		Update update = new Update();
		update.set("juniorNum", juniorNum);
		mongoTemplate.updateFirst(query, update, AgentDbo.class);
	}

	@Override
	public void updateAgentTypeForTypeChange(AgentType type) {
		Query query = new Query(Criteria.where("agentType.type").is(type.getType()));
		Update update = new Update();
		update.set("agentType.type", type.getType());
		update.set("agentType.memberReward", type.getMemberReward());
		update.set("agentType.juniorReward", type.getJuniorReward());
		mongoTemplate.updateMulti(query, update, AgentDbo.class);
	}
}
