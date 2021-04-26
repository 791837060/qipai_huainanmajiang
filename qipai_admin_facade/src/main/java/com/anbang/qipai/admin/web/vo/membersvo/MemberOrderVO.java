package com.anbang.qipai.admin.web.vo.membersvo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.anbang.qipai.admin.plan.bean.members.MemberOrder;

public class MemberOrderVO extends MemberOrder {
	private Long startTime;
	private Long endTime;
	private String productPriceSort;
	private String numberSort;
	private String totalamountSort;
	private String createTimeSort;
	private String statusSort;
	private String pay_typeSort;

	public Sort getSort() {
		List<Order> orderList = new ArrayList<>();
//		if ("ASC".equals(productPriceSort)) {
//			orderList.add(new Order(Direction.ASC, "productPrice"));
//		} else if ("DESC".equals(productPriceSort)) {
//			orderList.add(new Order(Direction.DESC, "productPrice"));
//		}
//		if ("ASC".equals(numberSort)) {
//			orderList.add(new Order(Direction.ASC, "number"));
//		} else if ("DESC".equals(numberSort)) {
//			orderList.add(new Order(Direction.DESC, "number"));
//		}
		if ("ASC".equals(totalamountSort)) {
			orderList.add(new Order(Direction.ASC, "totalamount"));
		} else if ("DESC".equals(totalamountSort)) {
			orderList.add(new Order(Direction.DESC, "totalamount"));
		}
		if ("ASC".equals(createTimeSort)) {
			orderList.add(new Order(Direction.ASC, "createTime"));
		} else if ("DESC".equals(createTimeSort)) {
			orderList.add(new Order(Direction.DESC, "createTime"));
		}
//		if ("ASC".equals(statusSort)) {
//			orderList.add(new Order(Direction.ASC, "status"));
//		} else if ("DESC".equals(statusSort)) {
//			orderList.add(new Order(Direction.DESC, "status"));
//		}
//		if ("ASC".equals(pay_typeSort)) {
//			orderList.add(new Order(Direction.ASC, "pay_type"));
//		} else if ("DESC".equals(pay_typeSort)) {
//			orderList.add(new Order(Direction.DESC, "pay_type"));
//		}
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

	public String getProductPriceSort() {
		return productPriceSort;
	}

	public void setProductPriceSort(String productPriceSort) {
		this.productPriceSort = productPriceSort;
	}

	public String getNumberSort() {
		return numberSort;
	}

	public void setNumberSort(String numberSort) {
		this.numberSort = numberSort;
	}

	public String getTotalamountSort() {
		return totalamountSort;
	}

	public void setTotalamountSort(String totalamountSort) {
		this.totalamountSort = totalamountSort;
	}

	public String getCreateTimeSort() {
		return createTimeSort;
	}

	public void setCreateTimeSort(String createTimeSort) {
		this.createTimeSort = createTimeSort;
	}

	public String getStatusSort() {
		return statusSort;
	}

	public void setStatusSort(String statusSort) {
		this.statusSort = statusSort;
	}

	public String getPay_typeSort() {
		return pay_typeSort;
	}

	public void setPay_typeSort(String pay_typeSort) {
		this.pay_typeSort = pay_typeSort;
	}
}
