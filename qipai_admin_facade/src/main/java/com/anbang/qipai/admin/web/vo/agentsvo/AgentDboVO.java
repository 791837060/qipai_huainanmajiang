package com.anbang.qipai.admin.web.vo.agentsvo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.anbang.qipai.admin.plan.bean.agents.AgentDbo;

public class AgentDboVO extends AgentDbo {
	private Long startTime;
	private Long endTime;
	private String inviteMemberNumSort;
	private String juniorNumSort;
	private String createTimeSort;
	private String stateSort;
	private String type;

	public Sort getSort() {
		List<Order> orderList = new ArrayList<>();
		if ("ASC".equals(inviteMemberNumSort)) {
			orderList.add(new Order(Direction.ASC, "inviteMemberNum"));
		} else if ("DESC".equals(inviteMemberNumSort)) {
			orderList.add(new Order(Direction.DESC, "inviteMemberNum"));
		}
		if ("ASC".equals(juniorNumSort)) {
			orderList.add(new Order(Direction.ASC, "juniorNum"));
		} else if ("DESC".equals(juniorNumSort)) {
			orderList.add(new Order(Direction.DESC, "juniorNum"));
		}
		if ("ASC".equals(createTimeSort)) {
			orderList.add(new Order(Direction.ASC, "createTime"));
		} else if ("DESC".equals(createTimeSort)) {
			orderList.add(new Order(Direction.DESC, "createTime"));
		}
		if ("ASC".equals(stateSort)) {
			orderList.add(new Order(Direction.ASC, "state"));
		} else if ("DESC".equals(stateSort)) {
			orderList.add(new Order(Direction.DESC, "state"));
		}
		if (!orderList.isEmpty()) {
			Sort sort = new Sort(orderList);
			return sort;
		}
		Sort sort = new Sort(new Order(Direction.DESC, "createTime"));
		return sort;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getInviteMemberNumSort() {
		return inviteMemberNumSort;
	}

	public void setInviteMemberNumSort(String inviteMemberNumSort) {
		this.inviteMemberNumSort = inviteMemberNumSort;
	}

	public String getJuniorNumSort() {
		return juniorNumSort;
	}

	public void setJuniorNumSort(String juniorNumSort) {
		this.juniorNumSort = juniorNumSort;
	}

	public String getCreateTimeSort() {
		return createTimeSort;
	}

	public void setCreateTimeSort(String createTimeSort) {
		this.createTimeSort = createTimeSort;
	}

	public String getStateSort() {
		return stateSort;
	}

	public void setStateSort(String stateSort) {
		this.stateSort = stateSort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
