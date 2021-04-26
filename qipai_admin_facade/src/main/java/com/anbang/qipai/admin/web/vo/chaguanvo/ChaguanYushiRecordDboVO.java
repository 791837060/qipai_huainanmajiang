package com.anbang.qipai.admin.web.vo.chaguanvo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanYushiRecordDbo;

public class ChaguanYushiRecordDboVO extends ChaguanYushiRecordDbo {
	private Long startTime;
	private Long endTime;
	private String createTimeSort;

	public Sort getSort() {
		List<Order> orderList = new ArrayList<>();
		if ("ASC".equals(createTimeSort)) {
			orderList.add(new Order(Direction.ASC, "createTime"));
		} else if ("DESC".equals(createTimeSort)) {
			orderList.add(new Order(Direction.DESC, "createTime"));
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
}
