package com.anbang.qipai.admin.web.vo.agentsvo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.anbang.qipai.admin.plan.bean.agents.AgentClubCardRecordDbo;

public class AgentClubCardRecordDboVO extends AgentClubCardRecordDbo {
	private Long startTime;
	private Long endTime;
	private String type;
	private String accountingTimeSort;
	private String costSort;
	private String balanceAfterZhouSort;
	private String balanceAfterYueSort;
	private String balanceAfterJiSort;

	public Sort getSort() {
		List<Order> orderList = new ArrayList<>();
		if ("ASC".equals(accountingTimeSort)) {
			orderList.add(new Order(Direction.ASC, "accountingTime"));
		} else if ("DESC".equals(accountingTimeSort)) {
			orderList.add(new Order(Direction.DESC, "accountingTime"));
		}
		if ("ASC".equals(costSort)) {
			orderList.add(new Order(Direction.ASC, "cost"));
		} else if ("DESC".equals(costSort)) {
			orderList.add(new Order(Direction.DESC, "cost"));
		}
		if ("ASC".equals(balanceAfterZhouSort)) {
			orderList.add(new Order(Direction.ASC, "balanceAfterZhou"));
		} else if ("DESC".equals(balanceAfterZhouSort)) {
			orderList.add(new Order(Direction.DESC, "balanceAfterZhou"));
		}
		if ("ASC".equals(balanceAfterYueSort)) {
			orderList.add(new Order(Direction.ASC, "balanceAfterYue"));
		} else if ("DESC".equals(balanceAfterYueSort)) {
			orderList.add(new Order(Direction.DESC, "balanceAfterYue"));
		}
		if ("ASC".equals(balanceAfterJiSort)) {
			orderList.add(new Order(Direction.ASC, "balanceAfterJi"));
		} else if ("DESC".equals(balanceAfterJiSort)) {
			orderList.add(new Order(Direction.DESC, "balanceAfterJi"));
		}
		if (!orderList.isEmpty()) {
			Sort sort = new Sort(orderList);
			return sort;
		}
		Sort sort = new Sort(new Order(Direction.DESC, "accountingTime"));
		return sort;
	}

	public String getBalanceAfterZhouSort() {
		return balanceAfterZhouSort;
	}

	public void setBalanceAfterZhouSort(String balanceAfterZhouSort) {
		this.balanceAfterZhouSort = balanceAfterZhouSort;
	}

	public String getBalanceAfterYueSort() {
		return balanceAfterYueSort;
	}

	public void setBalanceAfterYueSort(String balanceAfterYueSort) {
		this.balanceAfterYueSort = balanceAfterYueSort;
	}

	public String getBalanceAfterJiSort() {
		return balanceAfterJiSort;
	}

	public void setBalanceAfterJiSort(String balanceAfterJiSort) {
		this.balanceAfterJiSort = balanceAfterJiSort;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccountingTimeSort() {
		return accountingTimeSort;
	}

	public void setAccountingTimeSort(String accountingTimeSort) {
		this.accountingTimeSort = accountingTimeSort;
	}

	public String getCostSort() {
		return costSort;
	}

	public void setCostSort(String costSort) {
		this.costSort = costSort;
	}

}
