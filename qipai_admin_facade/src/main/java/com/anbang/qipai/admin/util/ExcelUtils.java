package com.anbang.qipai.admin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.anbang.qipai.admin.plan.bean.agents.AgentOrder;
import com.anbang.qipai.admin.plan.bean.agents.AgentRewardRecordDbo;
import com.anbang.qipai.admin.plan.bean.agents.AgentRewardType;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentOrderService;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentPayType;
import com.dml.accounting.TextAccountingSummary;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.anbang.qipai.admin.plan.bean.members.MemberOrder;

public class ExcelUtils {

	public static void generateOrderExcel(Integer rowid, Integer sheetNum, List<MemberOrder> list,
										   XSSFWorkbook workbook) {
		XSSFSheet spreadsheet = null;
		if (rowid > 20000) {
			sheetNum++;
			spreadsheet = workbook.createSheet("OrderInfo" + sheetNum);
			rowid = 0;
		} else {
			spreadsheet = workbook.createSheet("OrderInfo" + sheetNum);
			XSSFRow row = spreadsheet.createRow(rowid);
			row.createCell(0).setCellValue("订单编号");
			row.createCell(1).setCellValue("支付方式");
			row.createCell(2).setCellValue("第三方订单号");
			row.createCell(3).setCellValue("支付状态");
			row.createCell(4).setCellValue("支付人编号");
			row.createCell(5).setCellValue("支付人昵称");
			row.createCell(6).setCellValue("收货人编号");
			row.createCell(7).setCellValue("收货人昵称");
			row.createCell(8).setCellValue("商品编号");
			row.createCell(9).setCellValue("商品名称");
			row.createCell(10).setCellValue("商品价格");
			row.createCell(11).setCellValue("购买数量");
			row.createCell(12).setCellValue("赠送玉石/张");
			row.createCell(13).setCellValue("赠送礼券/张");
			row.createCell(14).setCellValue("赠送会员时间/张");
			row.createCell(15).setCellValue("总价");
			row.createCell(16).setCellValue("购买ip地址");
			row.createCell(17).setCellValue("下单时间");
			row.createCell(18).setCellValue("发货时间");
			rowid++;
		}
		for (MemberOrder order : list) {
			XSSFRow row = spreadsheet.createRow(rowid);
			row.createCell(0).setCellValue(order.getId());
			row.createCell(1).setCellValue(order.getPay_type());
			row.createCell(2).setCellValue(order.getTransaction_id());
			row.createCell(3).setCellValue(order.getStatus());
			row.createCell(4).setCellValue(order.getPayerId());
			row.createCell(5).setCellValue(order.getPayerName());
			row.createCell(6).setCellValue(order.getReceiverId());
			row.createCell(7).setCellValue(order.getReceiverName());
			row.createCell(8).setCellValue(order.getProductId());
			row.createCell(9).setCellValue(order.getProductName());
			row.createCell(10).setCellValue(order.getProductPrice());
			row.createCell(11).setCellValue(order.getNumber());
			row.createCell(12).setCellValue(order.getGold());
			row.createCell(13).setCellValue(order.getScore());
			row.createCell(14).setCellValue(new SimpleDateFormat("DDD天").format(new Date(order.getVipTime())));
			row.createCell(15).setCellValue(order.getTotalamount());
			row.createCell(16).setCellValue(order.getReqIP());
			row.createCell(17)
					.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(order.getCreateTime())));
			row.createCell(18)
					.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(order.getDeliveTime())));
			rowid++;
		}
	}

	public static void agentRewardExcel(Integer rowid, Integer sheetNum, List<AgentRewardRecordDbo> list,
										  XSSFWorkbook workbook) {
		XSSFSheet spreadsheet = null;
		if (rowid > 20000) {
			sheetNum++;
			spreadsheet = workbook.createSheet("OrderInfo" + sheetNum);
			rowid = 0;
		} else {
			spreadsheet = workbook.createSheet("OrderInfo" + sheetNum);
			XSSFRow row = spreadsheet.createRow(rowid);
			row.createCell(0).setCellValue("收益代理");
			row.createCell(1).setCellValue("代理昵称");
			row.createCell(2).setCellValue("上级代理");
			row.createCell(3).setCellValue("返利金额");
			row.createCell(4).setCellValue("返利类型");
			row.createCell(5).setCellValue("返利商品");
			row.createCell(6).setCellValue("支付金额");
			row.createCell(7).setCellValue("消费玩家");
			row.createCell(8).setCellValue("玩家昵称");
			row.createCell(9).setCellValue("玩家头像url");
			row.createCell(10).setCellValue("收益时间");
			rowid++;
		}
		for (AgentRewardRecordDbo order : list) {
			XSSFRow row = spreadsheet.createRow(rowid);
			row.createCell(0).setCellValue(order.getAgentId());
			row.createCell(1).setCellValue(order.getAgentName());
			row.createCell(2).setCellValue(order.getBossId());
			row.createCell(3).setCellValue(order.getAccountingAmount());
			TextAccountingSummary summary = (TextAccountingSummary) order.getSummary();
			String rewardType = AgentRewardType.getRewardType(summary.getText());
			row.createCell(4).setCellValue(rewardType);
			row.createCell(5).setCellValue(order.getPruduct());
			row.createCell(6).setCellValue(order.getTotalamount());
			row.createCell(7).setCellValue(order.getMemberId());
			row.createCell(8).setCellValue(order.getMemberName());
			row.createCell(9).setCellValue(order.getMemberHeadimgurl());
			row.createCell(10)
					.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(order.getAccountingTime())));
			rowid++;
		}
	}

	public static void agentOrderExcel(Integer rowid, Integer sheetNum, List<AgentOrder> list,
										XSSFWorkbook workbook) {
		XSSFSheet spreadsheet = null;
		if (rowid > 20000) {
			sheetNum++;
			spreadsheet = workbook.createSheet("OrderInfo" + sheetNum);
			rowid = 0;
		} else {
			spreadsheet = workbook.createSheet("OrderInfo" + sheetNum);
			XSSFRow row = spreadsheet.createRow(rowid);
			row.createCell(0).setCellValue("订单编号");
			row.createCell(1).setCellValue("支付流水");
			row.createCell(2).setCellValue("充值类型");
			row.createCell(3).setCellValue("推广员ID");
			row.createCell(4).setCellValue("推广员昵称");
			row.createCell(5).setCellValue("订单金额");
			row.createCell(6).setCellValue("购买物品");
			row.createCell(7).setCellValue("充值地址IP");
			row.createCell(8).setCellValue("充值时间");
			rowid++;
		}
		for (AgentOrder order : list) {
			XSSFRow row = spreadsheet.createRow(rowid);
			row.createCell(0).setCellValue(order.getId());
			row.createCell(1).setCellValue(order.getTransaction_id());
			row.createCell(2).setCellValue(AgentPayType.getSummaryText(order.getPay_type()));
			row.createCell(3).setCellValue(order.getReceiverId());
			row.createCell(4).setCellValue(order.getReceiverName());
			row.createCell(5).setCellValue(order.getTotalamount());
			row.createCell(6).setCellValue(order.getProductName());
			row.createCell(7).setCellValue(order.getReqIP());
			row.createCell(8)
					.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(order.getDeliveTime())));
			rowid++;
		}
	}
}
