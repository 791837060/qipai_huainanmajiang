package com.anbang.qipai.admin.plan.dao.agentsdao;

import com.anbang.qipai.admin.plan.bean.agents.AgentOrder;
import com.anbang.qipai.admin.web.query.AgentOrderQuery;

import java.util.List;

public interface AgentOrderDao {

	public void addOrder(AgentOrder order);

	void orderFinished(String id, String transaction_id, String status, long deliveTime);

	double findAmountByBean(AgentOrderQuery agentOrderQuery);

	long countByBean(AgentOrderQuery agentOrderQuery);

	List<AgentOrder> findAgentOrderByBean(int page, int size, AgentOrderQuery agentOrderQuery);

	double sumField(AgentOrderQuery agentOrderQuery, String field);
}
