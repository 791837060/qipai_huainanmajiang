package com.anbang.qipai.admin.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.anbang.qipai.admin.plan.bean.agents.AgentOrder;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentOrderService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentRewardRecordDboService;
import com.anbang.qipai.admin.web.query.AgentOrderQuery;
import com.anbang.qipai.admin.web.vo.agentsvo.AdminMappingVO;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentRewardRecordDboVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.plan.service.membersservice.MemberOrderService;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.anbang.qipai.admin.web.vo.membersvo.MemberOrderVO;
import com.highto.framework.web.page.ListPage;

/**
 * 财务系统>代理消费、收益记录controller
 * 
 * @author 林少聪 2018.7.9
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
public class MemberOrderController {
	@Autowired
	private MemberOrderService orderService;

	@Autowired
	private AgentRewardRecordDboService agentRewardRecordDboService;

	@Autowired
	private AgentOrderService agentOrderService;

	/**
	 * 原订单管理
	 */
	@Deprecated
	@RequestMapping(value = "/queryorder", method = RequestMethod.POST)
	public CommonVO queryOrder(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, MemberOrderVO order) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		ListPage listPage = orderService.findOrderByConditions(page, size, order);
		data.put("listPage", listPage);
		double payCost = orderService.countPayCostByConditions(order);
		data.put("payCost", payCost);
		int payAmount = orderService.countPayAmountByConditions(order);
		data.put("payAmount", payAmount);
		double notPayCost = orderService.countNotPayCostByConditions(order);
		data.put("notPayCost", notPayCost);
		int notPayAmount = orderService.countNotPayAmountByConditions(order);
		data.put("notPayAmount", notPayAmount);
		vo.setSuccess(true);
		vo.setMsg("orderList");
		vo.setData(data);
		return vo;
	}

	/**
	 * 原充值记录
	 */
	@Deprecated
	@RequestMapping(value = "/queryrecharge", method = RequestMethod.POST)
	public CommonVO queryCharge(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, MemberOrderVO order) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		order.setStatus("PAYSUCCESS");
		ListPage listPage = orderService.findOrderByConditions(page, size, order);
		data.put("listPage", listPage);
		double cost = orderService.countCost();
		data.put("cost", cost);
		vo.setSuccess(true);
		vo.setMsg("orderList");
		vo.setData(data);
		return vo;
	}

	/**
	 * 订单管理导出
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public CommonVO downLoad(MemberOrderVO order, HttpServletResponse response) {
		CommonVO vo = new CommonVO();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String fileName = format.format(date) + "order.xlsx";
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		try {
			OutputStream output = response.getOutputStream();
			orderService.exportOrder(order, output);
			output.close();
		} catch (IOException e) {
			vo.setSuccess(false);
			vo.setMsg("IOException");
		}
		vo.setSuccess(true);
		vo.setMsg("orderList");
		return vo;
	}

	/**
	 * 代理收益查询
	 */
	@RequestMapping(value = "/queryagentreward", method = RequestMethod.POST)
	public CommonVO queryAgentReward(@RequestParam(defaultValue = "1") int page,
									 @RequestParam(defaultValue = "50") int size, AgentRewardRecordDboVO record) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		ListPage listPage = agentRewardRecordDboService.findAgentRewardRecordDboByConditions(page, size, record);
		data.put("listPage", listPage);
		double totalReword = agentRewardRecordDboService.findAmountByConditions(record);
		data.put("totalReward", totalReword);
		data.put("totalOrder", listPage.getTotalItemsCount());
		vo.setSuccess(true);
		vo.setMsg("agent reward");
		vo.setData(data);
		return vo;
	}

	/**
	 * 代理收益excel export
	 */
	@RequestMapping(value = "/agentrewardexport", method = RequestMethod.GET)
	public CommonVO agentRewardExport(AgentRewardRecordDboVO order, HttpServletResponse response) {
		CommonVO vo = new CommonVO();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String fileName = format.format(date) + "AgentReward.xlsx";
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		try {
			OutputStream output = response.getOutputStream();
			agentRewardRecordDboService.exportOrder(order, output);
			output.close();
		} catch (IOException e) {
			vo.setSuccess(false);
			vo.setMsg("IOException");
		}
		vo.setSuccess(true);
		vo.setMsg("orderList");
		return vo;
	}

    /**
     * 代理消费查询
     */
    @RequestMapping(value = "/queryagentcost", method = RequestMethod.POST)
    public CommonVO queryAgentCost(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "50") int size, AgentOrderQuery agentOrder) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap<>();

        agentOrder.setStatus("SUCCESS");  //默认查询已支付的记录

        ListPage listPage = agentOrderService.findAgentOrderByBean(page, size, agentOrder);
        data.put("listPage", listPage);
        double totalReword = agentOrderService.findAmountByBean(agentOrder);
        data.put("totalReward", totalReword);
        data.put("totalOrder", listPage.getTotalItemsCount());
        vo.setSuccess(true);
        vo.setMsg("queryAgentCost success");
        vo.setData(data);
        return vo;
    }

	/**
	 * 代理消费excel export
	 */
	@RequestMapping(value = "/agentcostexport", method = RequestMethod.GET)
	public CommonVO agentCostExport(AgentOrderQuery order, HttpServletResponse response) {
		CommonVO vo = new CommonVO();
		order.setStatus("SUCCESS");  //默认查询已支付的记录
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String fileName = format.format(date) + "Agentcost.xlsx";
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		try {
			OutputStream output = response.getOutputStream();
			agentOrderService.exportOrder(order, output);
			output.close();
		} catch (IOException e) {
			vo.setSuccess(false);
			vo.setMsg("IOException");
		}
		vo.setSuccess(true);
		vo.setMsg("orderList");
		return vo;
	}

	/**
	 * 查询充值记录
	 */
	@RequestMapping(value = "/queryrechargerecord", method = RequestMethod.POST)
	public CommonVO queryReChargeRecord(@RequestParam(value = "page", defaultValue = "1") Integer page,
								@RequestParam(value = "size", defaultValue = "50") Integer size, MemberOrderVO order) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		order.setStatus("PAYSUCCESS");
		ListPage listPage = orderService.findOrderByConditions(page, size, order);
		data.put("listPage", listPage);
		double totalCost = orderService.countPayCostByConditions(order);
		data.put("totalCost", totalCost);
		data.put("totalRecord", listPage.getTotalItemsCount());
		vo.setSuccess(true);
		vo.setMsg("orderList");
		vo.setData(data);
		return vo;
	}

	/**
	 * 充值记录excel export
	 */
	@RequestMapping(value = "/rechargedownload", method = RequestMethod.GET)
	public CommonVO rechargeDownLoad(MemberOrderVO order, HttpServletResponse response) {
		CommonVO vo = new CommonVO();
		order.setStatus("PAYSUCCESS");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String fileName = format.format(date) + "order.xlsx";
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setContentType("application/msexcel");
		try {
			OutputStream output = response.getOutputStream();
			orderService.exportOrder(order, output);
			output.close();
		} catch (IOException e) {
			vo.setSuccess(false);
			vo.setMsg("IOException");
		}
		vo.setSuccess(true);
		vo.setMsg("orderList");
		return vo;
	}

	/**
	 * 枚举显示映射
	 */
	@RequestMapping(value = "/adminmapping")
	public CommonVO adminMapping() {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		data.put("agentPayType", AdminMappingVO.agentPayType);
		vo.setSuccess(true);
		vo.setMsg("success");
		vo.setData(data);
		return vo;
	}

}
