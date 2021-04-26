package com.anbang.qipai.admin.web.controller;

import java.util.*;

import com.anbang.qipai.admin.constant.Constants;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentOrderService;
import com.anbang.qipai.admin.util.CommonVOUtil;
import com.anbang.qipai.admin.util.FormatUtils;
import com.anbang.qipai.admin.util.TimeUtil;
import com.anbang.qipai.admin.web.query.AgentOrderQuery;
import com.anbang.qipai.admin.web.vo.membersvo.MemberOrderVO;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.plan.service.agentsservice.AgentClubCardRecordDboService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentDboService;
import com.anbang.qipai.admin.plan.service.membersservice.MemberDboService;
import com.anbang.qipai.admin.plan.service.membersservice.MemberOrderService;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentDboVO;

@CrossOrigin
@RestController
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private AgentDboService agentDboService;

	@Autowired
	private MemberDboService memberService;

	@Autowired
	private MemberOrderService orderService;

	@Autowired
	private AgentClubCardRecordDboService agentClubCardRecordDboService;

	@Autowired
	private AgentOrderService agentOrderService;

    /**
     * 首页数据总统计
     */
	@RequestMapping("/statistics")
	public CommonVO statistics(Integer year) {
		CommonVO vo = new CommonVO();
		Map<String, Object> data = new HashMap<String, Object>();
		long amount = memberService.countAmount();
		data.put("amount", amount);
		long vipAmount = memberService.countVipMember();
		data.put("vipAmount", vipAmount);
		long agentAmount = agentDboService.countAmountByConditions(new AgentDboVO());
		data.put("agentAmount", agentAmount);

		//年销售数据
		Pair<Long, Long> pair = TimeUtil.getYearRange(year);
		MemberOrderVO memberOrderVO = new MemberOrderVO();
		memberOrderVO.setStatus("PAYSUCCESS");
		memberOrderVO.setStartTime(pair.getKey());
		memberOrderVO.setEndTime(pair.getValue());
		double memberExpence = orderService.sumField(memberOrderVO, "totalamount");
		AgentOrderQuery agentOrderQuery = new AgentOrderQuery();
		agentOrderQuery.setStatus("SUCCESS");
		agentOrderQuery.setStartTime(pair.getKey());
		agentOrderQuery.setEndTime(pair.getValue());
		double agentExpence = agentOrderService.sumField(agentOrderQuery, "totalamount");
		data.put("totalSale", memberExpence + agentExpence);

		vo.setSuccess(true);
		vo.setMsg("statistics info");
		vo.setData(data);
		return vo;
	}

    /**
     * 首页销售详情
     */
	@RequestMapping("/salesDetails")
	public CommonVO salesDetails(Integer yearMonth) {
		CommonVO vo = new CommonVO();
		Map<String, Object> data = new HashMap<String, Object>();
		if (yearMonth == null) {
			vo.setSuccess(false);
			vo.setMsg("Missing required input parameters");
			return vo;
		}

		if (!FormatUtils.monthCheck(yearMonth)) {
			return CommonVOUtil.error("Parameter format error");
		}

		//玩家消费
		MemberOrderVO memberOrderVO = new MemberOrderVO();
		memberOrderVO.setStatus("PAYSUCCESS");
		memberOrderVO.setOrderMonth(yearMonth);
		memberOrderVO.setProductName("日卡");
		double memberRiNum = orderService.sumField(memberOrderVO, "number");
		double memberRiAmount = orderService.sumField(memberOrderVO, "totalamount");
		memberOrderVO.setProductName("周卡");
		double memberZhouNum = orderService.sumField(memberOrderVO, "number");
		double memberZhouAmount = orderService.sumField(memberOrderVO, "totalamount");
		memberOrderVO.setProductName("月卡");
		double memberYueNum = orderService.sumField(memberOrderVO, "number");
		double memberYueAmount = orderService.sumField(memberOrderVO, "totalamount");
		memberOrderVO.setProductName("季卡");
		double memberJiNum = orderService.sumField(memberOrderVO, "number");
		double memberJiAmount = orderService.sumField(memberOrderVO, "totalamount");
		data.put("memberRiNum",memberRiNum);
		data.put("memberRiAmount",memberRiAmount);
		data.put("memberZhouNum",memberZhouNum);
		data.put("memberZhouAmount",memberZhouAmount);
		data.put("memberYueNum",memberYueNum);
		data.put("memberYueAmount",memberYueAmount);
		data.put("memberJiNum",memberJiNum);
		data.put("memberJiAmount",memberJiAmount);

		//平台消费
		AgentOrderQuery agentOrderQuery = new AgentOrderQuery();
		agentOrderQuery.setStatus("SUCCESS");
		agentOrderQuery.setOrderMonth(yearMonth);
		agentOrderQuery.setProductName("日卡");
		double agentRiNum = agentOrderService.sumField(agentOrderQuery, "number");
		double agentRiAmount = agentOrderService.sumField(agentOrderQuery, "totalamount");
		agentOrderQuery.setProductName("周卡");
		double agentZhouNum = agentOrderService.sumField(agentOrderQuery, "number");
		double agentZhouAmount = agentOrderService.sumField(agentOrderQuery, "totalamount");
		agentOrderQuery.setProductName("月卡");
		double agentYueNum = agentOrderService.sumField(agentOrderQuery, "number");
		double agentYueAmount = agentOrderService.sumField(agentOrderQuery, "totalamount");
		agentOrderQuery.setProductName("季卡");
		double agentJiNum = agentOrderService.sumField(agentOrderQuery, "number");
		double agentJiAmount = agentOrderService.sumField(agentOrderQuery, "totalamount");
		agentOrderQuery.setProductName("玉石");
		double agentYuNum = agentOrderService.sumField(agentOrderQuery, "number");
		double agentYuAmount = agentOrderService.sumField(agentOrderQuery, "totalamount");
		data.put("agentRiNum",agentRiNum);
		data.put("agentRiAmount",agentRiAmount);
		data.put("agentZhouNum",agentZhouNum);
		data.put("agentZhouAmount",agentZhouAmount);
		data.put("agentYueNum",agentYueNum);
		data.put("agentYueAmount",agentYueAmount);
		data.put("agentJiNum",agentJiNum);
		data.put("agentJiAmount",agentJiAmount);
		data.put("agentYuNum",agentYuNum);
		data.put("agentYuAmount",agentYuAmount);

		vo.setSuccess(true);
		vo.setMsg("index info");
		vo.setData(data);
		return vo;
	}

    /**
     * 首页柱形图
     */
	@RequestMapping("/histogram")
	public CommonVO histogram(Integer year) {
		CommonVO vo = new CommonVO();
		List<Map> histogramList = new ArrayList<>();

		if (year == null) {
			Calendar currCal = Calendar.getInstance();
			year = currCal.get(Calendar.YEAR);
		}
		for (int i = 0; i < Constants.MONTHLIST.length; i++) {
			Map histogramItem = new HashMap();
			int month = Integer.valueOf(year + Constants.MONTHLIST[i]);
			//月代理购买
			AgentOrderQuery agentOrderQuery = new AgentOrderQuery();
			agentOrderQuery.setStatus("SUCCESS");
			agentOrderQuery.setOrderMonth(month);
			double agentBuy = agentOrderService.sumField(agentOrderQuery, "totalamount");
			//月玩家购买
			MemberOrderVO memberOrderVO = new MemberOrderVO();
			memberOrderVO.setStatus("PAYSUCCESS");
			memberOrderVO.setOrderMonth(month);
			double memberBuy = orderService.sumField(memberOrderVO, "totalamount");
			histogramItem.put("agentBuy", agentBuy);
			histogramItem.put("memberBuy", memberBuy);
			histogramItem.put("totalAmount", agentBuy + memberBuy);
			histogramList.add(histogramItem);
		}

		vo.setSuccess(true);
		vo.setMsg("histogram info");
		vo.setData(histogramList);
		return vo;
	}
}
