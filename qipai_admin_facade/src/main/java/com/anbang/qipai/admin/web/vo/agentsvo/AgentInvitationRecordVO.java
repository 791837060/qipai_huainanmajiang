package com.anbang.qipai.admin.web.vo.agentsvo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.anbang.qipai.admin.plan.bean.agents.AgentInvitationRecord;

public class AgentInvitationRecordVO extends AgentInvitationRecord {
	private Long startTime;
	private Long endTime;
	private String createTimeSort;
	private String inviteNumSort;
	private String scoreSort;

	public Sort getSort() {
		List<Order> orderList = new ArrayList<>();
		if ("ASC".equals(createTimeSort)) {
			orderList.add(new Order(Direction.ASC, "createTime"));
		} else if ("DESC".equals(createTimeSort)) {
			orderList.add(new Order(Direction.DESC, "createTime"));
		}
		if ("ASC".equals(inviteNumSort)) {
			orderList.add(new Order(Direction.ASC, "inviteNum"));
		} else if ("DESC".equals(inviteNumSort)) {
			orderList.add(new Order(Direction.DESC, "inviteNum"));
		}
		if ("ASC".equals(scoreSort)) {
			orderList.add(new Order(Direction.ASC, "score"));
		} else if ("DESC".equals(scoreSort)) {
			orderList.add(new Order(Direction.DESC, "score"));
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

	public String getCreateTimeSort() {
		return createTimeSort;
	}

	public void setCreateTimeSort(String createTimeSort) {
		this.createTimeSort = createTimeSort;
	}

	public String getInviteNumSort() {
		return inviteNumSort;
	}

	public void setInviteNumSort(String inviteNumSort) {
		this.inviteNumSort = inviteNumSort;
	}

	public String getScoreSort() {
		return scoreSort;
	}

	public void setScoreSort(String scoreSort) {
		this.scoreSort = scoreSort;
	}

}
