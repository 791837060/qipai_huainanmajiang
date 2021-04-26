package com.anbang.qipai.admin.plan.service.membersservice;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.anbang.qipai.admin.util.TimeUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.members.MemberOrder;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberOrderDao;
import com.anbang.qipai.admin.util.ExcelUtils;
import com.anbang.qipai.admin.web.vo.membersvo.MemberOrderStatus;
import com.anbang.qipai.admin.web.vo.membersvo.MemberOrderVO;
import com.anbang.qipai.admin.web.vo.membersvo.MemberPayType;
import com.highto.framework.web.page.ListPage;

@Service
public class MemberOrderService {

	@Autowired
	private MemberOrderDao orderDao;

	public void addOrder(MemberOrder order) {
		order.setOrderMonth(TimeUtil.getNowMonth(order.getCreateTime(),""));
		orderDao.addOrder(order);
	}

	public void orderFinished(String id, String transaction_id, String status, long deliveTime) {
		orderDao.orderFinished(id, transaction_id, status, deliveTime);
	}

	public ListPage findOrderByConditions(int page, int size, MemberOrderVO order) {
		long amount = orderDao.getAmountByConditions(order);
		List<MemberOrder> orderList = orderDao.findOrderByConditions(page, size, order);
		for (int i = 0; i < orderList.size(); i++) {
			String status = orderList.get(i).getStatus();
			orderList.get(i).setStatus(MemberOrderStatus.getSummaryText(status));
			String payType = orderList.get(i).getPay_type();
			orderList.get(i).setPay_type(MemberPayType.getSummaryText(payType));
		}
		ListPage listPage = new ListPage(orderList, page, size, (int) amount);
		return listPage;
	}

	public int countPayAmountByConditions(MemberOrderVO order) {
		return orderDao.countPayAmountByConditions(order);
	}

	public int countNotPayAmountByConditions(MemberOrderVO order) {
		return orderDao.countNotPayAmountByConditions(order);
	}

	public double countPayCostByConditions(MemberOrderVO order) {
		return orderDao.countPayCostByConditions(order);
	}

	public double countNotPayCostByConditions(MemberOrderVO order) {
		return orderDao.countNotPayCostByConditions(order);
	}

	public double countCost() {
		return orderDao.countCost();
	}

	public double countCostByTime(long startTime, long endTime) {
		return orderDao.countCostByTime(startTime, endTime);
	}

	public double countCostByMemberId(String memberId) {
		return orderDao.countCostByMemberId(memberId);
	}

	public void exportOrder(MemberOrderVO order, OutputStream output) throws IOException {
		Integer rowid = 0;
		Integer sheetNum = 1;
		XSSFWorkbook workbook = new XSSFWorkbook();
		long amount = orderDao.getAmountByConditions(order);
		int size = 300;
		long pageNum = amount % size > 0 ? amount / size + 1 : amount / size;
		for (int page = 1; page <= pageNum; page++) {
			List<MemberOrder> orderList = orderDao.findOrderByConditions(page, size, order);
			ExcelUtils.generateOrderExcel(rowid, sheetNum, orderList, workbook);
			sheetNum++;
		}
		workbook.write(output);
		workbook.close();
	}

	public int countProductNumByTimeAndProduct(String productName, long startTime, long endTime) {
		return orderDao.countProductNumByTimeAndProduct(productName, startTime, endTime);
	}

	public double sumField(MemberOrderVO memberOrderVO, String field) {
		return orderDao.sumField(memberOrderVO, field);
	}

	/**
	 * 月付费玩家
	 */
	public int countMonthPayPlayer(Integer orderMonth) {
		return orderDao.countMonthPayPlayer(orderMonth);
	}
}
