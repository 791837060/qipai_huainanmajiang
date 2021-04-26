package com.anbang.qipai.admin.plan.service.agentsservice;

import com.anbang.qipai.admin.util.ExcelUtils;
import com.anbang.qipai.admin.util.TimeUtil;
import com.anbang.qipai.admin.web.query.AgentOrderQuery;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentPayType;
import com.highto.framework.web.page.ListPage;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.agents.AgentOrder;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentOrderDao;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class AgentOrderService {

	@Autowired
	private AgentOrderDao orderDao;

	@Autowired
	private AgentClubCardService agentClubCardService;

	public void addOrder(AgentOrder order) {
		order.setOrderMonth(TimeUtil.getNowMonth(order.getCreateTime(), ""));
		orderDao.addOrder(order);
	}
	
	public void orderFinished(String id, String transaction_id, String status, long deliveTime) {
		orderDao.orderFinished(id, transaction_id, status, deliveTime);
	}

	public double findAmountByBean(AgentOrderQuery agentOrderQuery){
		return orderDao.findAmountByBean(agentOrderQuery);
	}

	public ListPage findAgentOrderByBean(int page, int size, AgentOrderQuery agentOrderQuery) {
		long count = orderDao.countByBean(agentOrderQuery);
		List<AgentOrder> agentOrders = orderDao.findAgentOrderByBean(page,size,agentOrderQuery);
		for(AgentOrder list : agentOrders) {
			list.setPay_type(AgentPayType.getSummaryText(list.getPay_type()));

			//获取产品数量
			int productNum = agentClubCardService.queryProductNum(list.getProductId());
			list.setProductName(conversion(list.getProductName(),list.getNumber(), productNum));
		}
		ListPage listPage = new ListPage(agentOrders, page, size, (int)count);
		return listPage;
	}

	public void exportOrder(AgentOrderQuery agentOrderQuery, OutputStream output) throws IOException {
		Integer rowid = 0;
		Integer sheetNum = 1;
		XSSFWorkbook workbook = new XSSFWorkbook();
		long amount = orderDao.countByBean(agentOrderQuery);
		int size = 300;
		long pageNum = amount % size > 0 ? amount / size + 1 : amount / size;
		for (int page = 1; page <= pageNum; page++) {
			List<AgentOrder> agentOrders = orderDao.findAgentOrderByBean(page,size,agentOrderQuery);

			//获取产品数量
			for(AgentOrder list : agentOrders) {
				int productNum = agentClubCardService.queryProductNum(list.getProductId());
				list.setProductName(conversion(list.getProductName(),list.getNumber(), productNum));
			}

			ExcelUtils.agentOrderExcel(rowid, sheetNum, agentOrders, workbook);
			sheetNum++;
		}
		workbook.write(output);
		workbook.close();
	}

	public double sumField(AgentOrderQuery agentOrderQuery, String field) {
		return orderDao.sumField(agentOrderQuery, field);
	}


	//显示转换
	public static String conversion(String productName, int number, int	productNum) {
		if (StringUtils.contains(productName,"卡")) {
			return productName + number + "*" + productNum + "张";
		}
		if ("玉石".equals(productName)) {
			return productName + number + "*" + productNum + "个";
		}
		return productName;
	}
}
