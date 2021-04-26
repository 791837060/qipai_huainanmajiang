package com.anbang.qipai.admin.web.vo.agentsvo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.anbang.qipai.admin.plan.bean.agents.AgentScoreRecordDbo;

public class AgentScoreRecordDboVO extends AgentScoreRecordDbo {
	private Long startTime;
	private Long endTime;
	private String type;
	private String accountingAmountSort;
	private String accountingTimeSort;
	private String balanceAfterSort;
	private String numberSort;

	public Sort getSort() {
		List<Order> orderList = new ArrayList<>();
		if ("ASC".equals(accountingAmountSort)) {
			orderList.add(new Order(Direction.ASC, "accountingAmount"));
		} else if ("DESC".equals(accountingAmountSort)) {
			orderList.add(new Order(Direction.DESC, "accountingAmount"));
		}
		if ("ASC".equals(accountingTimeSort)) {
			orderList.add(new Order(Direction.ASC, "accountingTime"));
		} else if ("DESC".equals(accountingTimeSort)) {
			orderList.add(new Order(Direction.DESC, "accountingTime"));
		}
		if ("ASC".equals(balanceAfterSort)) {
			orderList.add(new Order(Direction.ASC, "balanceAfter"));
		} else if ("DESC".equals(balanceAfterSort)) {
			orderList.add(new Order(Direction.DESC, "balanceAfter"));
		}
		if ("ASC".equals(numberSort)) {
			orderList.add(new Order(Direction.ASC, "number"));
		} else if ("DESC".equals(numberSort)) {
			orderList.add(new Order(Direction.DESC, "number"));
		}
		if (!orderList.isEmpty()) {
			Sort sort = new Sort(orderList);
			return sort;
		}
		Sort sort = new Sort(new Order(Direction.DESC, "accountingTime"));
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccountingAmountSort() {
		return accountingAmountSort;
	}

	public void setAccountingAmountSort(String accountingAmountSort) {
		this.accountingAmountSort = accountingAmountSort;
	}

	public String getAccountingTimeSort() {
		return accountingTimeSort;
	}

	public void setAccountingTimeSort(String accountingTimeSort) {
		this.accountingTimeSort = accountingTimeSort;
	}

	public String getBalanceAfterSort() {
		return balanceAfterSort;
	}

	public void setBalanceAfterSort(String balanceAfterSort) {
		this.balanceAfterSort = balanceAfterSort;
	}

	public String getNumberSort() {
		return numberSort;
	}

	public void setNumberSort(String numberSort) {
		this.numberSort = numberSort;
	}

}
