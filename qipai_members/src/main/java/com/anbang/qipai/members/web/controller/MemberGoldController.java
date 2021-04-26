package com.anbang.qipai.members.web.controller;

import com.anbang.qipai.members.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.members.cqrs.c.service.MemberAuthService;
import com.anbang.qipai.members.cqrs.c.service.MemberGoldCmdService;
import com.anbang.qipai.members.cqrs.q.dbo.MemberGoldAccountDbo;
import com.anbang.qipai.members.cqrs.q.dbo.MemberGoldRecordDbo;
import com.anbang.qipai.members.cqrs.q.service.MemberGoldQueryService;
import com.anbang.qipai.members.msg.service.GoldsMsgService;
import com.anbang.qipai.members.plan.service.MemberYushiService;
import com.anbang.qipai.members.web.vo.CommonVO;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/gold")
public class MemberGoldController {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private MemberGoldCmdService memberGoldCmdService;

	@Autowired
	private MemberGoldQueryService memberGoldQueryService;

	@Autowired
	private GoldsMsgService goldsMsgService;

	@Autowired
	private MemberYushiService memberYushiService;

	@RequestMapping(value = "/account")
	@ResponseBody
	public CommonVO account(String token) {
		CommonVO vo = new CommonVO();
		String memberId=memberAuthService.getMemberIdBySessionId(token);
		if(memberId==null){
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		MemberGoldAccountDbo account=memberGoldQueryService.findMemberGoldAccount(memberId);
		Map data=new HashMap<>();
		vo.setData(data);
		data.put("gold",0);
		if(account!=null){
			data.put("gold",account.getBalance());
		}
		return vo;
	}
	@RequestMapping(value = "/withdraw")
	@ResponseBody
	public CommonVO withdraw(String memberId, int amount, String textSummary) {
		CommonVO vo = new CommonVO();
		try {
			AccountingRecord rcd = memberGoldCmdService.withdraw(memberId, amount, textSummary,
					System.currentTimeMillis());
			//修改三个表,memberGoldRecord,memberGoldAccount,memberDbo
			MemberGoldRecordDbo dbo = memberGoldQueryService.withdraw(memberId, rcd);
			// game系统,admin系统更新表
			goldsMsgService.withdraw(dbo);
			return vo;
		} catch (InsufficientBalanceException e) {
			vo.setSuccess(false);
			vo.setMsg("InsufficientBalanceException");
			return vo;
		} catch (MemberNotFoundException e) {
			vo.setSuccess(false);
			vo.setMsg("MemberNotFoundException");
			return vo;
		}
	}

	@RequestMapping(value = "/givegoldtomember")
	public CommonVO giveGoldToMember(String memberId, int amount, String textSummary) {
		CommonVO vo = new CommonVO();
		try {
			AccountingRecord rcd = memberGoldCmdService.giveGoldToMember(memberId, amount, textSummary,
					System.currentTimeMillis());
			MemberGoldRecordDbo dbo = memberGoldQueryService.withdraw(memberId, rcd);
			// rcd发kafka
			goldsMsgService.withdraw(dbo);
			return vo;
		} catch (MemberNotFoundException e) {
			vo.setSuccess(false);
			vo.setMsg("MemberNotFoundException");
			return vo;
		}
	}

	@RequestMapping(value = "/members_withdraw")
	@ResponseBody
	public CommonVO membersWithdraw(@RequestBody String[] memberIds, int amount, String textSummary) {
		CommonVO vo = new CommonVO();
		try {
			for (String memberId : memberIds) {
				AccountingRecord rcd = memberGoldCmdService.withdraw(memberId, amount, textSummary,
						System.currentTimeMillis());
				MemberGoldRecordDbo dbo = memberGoldQueryService.withdraw(memberId, rcd);
				// rcd发kafka
				goldsMsgService.withdraw(dbo);
			}
		} catch (InsufficientBalanceException e) {

		} catch (MemberNotFoundException e) {

		}
		vo.setSuccess(true);
		return vo;
	}

	@RequestMapping(value = "/givegoldtomembers")
	public CommonVO giveGoldToMembers(@RequestBody String[] memberIds, int amount, String textSummary) {
		CommonVO vo = new CommonVO();
		try {
			for (String memberId : memberIds) {
				AccountingRecord rcd = memberGoldCmdService.giveGoldToMember(memberId, amount, textSummary,
						System.currentTimeMillis());
				MemberGoldRecordDbo dbo = memberGoldQueryService.withdraw(memberId, rcd);
				// rcd发kafka
				goldsMsgService.withdraw(dbo);
			}
		} catch (MemberNotFoundException e) {
		}
		vo.setSuccess(true);
		return vo;
	}
	/**
	 * 玉石购买列表
	 * @param token
	 * @return
	 */

	@RequestMapping("/queryyushi")
	public CommonVO queryYushi(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Map data = new HashMap<>();
		List<Map<String,Object>> yushiList = memberYushiService.findAllYushi(memberId);
		data.put("yushiList", yushiList);
		vo.setData(data);
		vo.setSuccess(true);
		vo.setMsg("yushiList");
		return vo;
	}

}
