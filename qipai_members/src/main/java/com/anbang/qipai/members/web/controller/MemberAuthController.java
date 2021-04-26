package com.anbang.qipai.members.web.controller;

import com.anbang.qipai.members.cqrs.c.service.MemberAuthService;
import com.anbang.qipai.members.msg.service.MemberLoginLimitRecordMsgService;
import com.anbang.qipai.members.plan.bean.MemberLoginIPLimit;
import com.anbang.qipai.members.plan.bean.MemberLoginLimitRecord;
import com.anbang.qipai.members.plan.service.MemberLoginIPLimitService;
import com.anbang.qipai.members.plan.service.MemberLoginLimitRecordService;
import com.anbang.qipai.members.util.IPUtil;
import com.anbang.qipai.members.web.vo.CommonVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class MemberAuthController {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private MemberLoginLimitRecordService memberLoginLimitRecordService;

	@Autowired
	private MemberLoginLimitRecordMsgService memberLoginLimitRecordMsgService;

	@Autowired
	private MemberLoginIPLimitService memberLoginIPLimitService;

//	first
	@RequestMapping(value = "/trytoken")
	@ResponseBody
	public CommonVO trytoken(String token) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId != null) {
			MemberLoginLimitRecord record = memberLoginLimitRecordService.findByMemberId(memberId, true);
			if (record != null) {
				vo.setSuccess(false);
				vo.setMsg("login limited");
				return vo;
			}
			Map data = new HashMap();
			data.put("memberId", memberId);

			vo.setData(data);
		} else {
			vo.setSuccess(false);
		}

		return vo;
	}

	/**
	 * 拆分限制登录校验，供u3d使用
	 */
	@RequestMapping(value = "/checkToken")
	@ResponseBody
	public CommonVO checkToken(String token, HttpServletRequest request) {
		CommonVO vo = new CommonVO();
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId != null) {
			MemberLoginLimitRecord record = memberLoginLimitRecordService.findByMemberId(memberId, true);
			if (record != null) {
				vo.setSuccess(false);
				vo.setMsg("login limited");
				return vo;
			}

			String loginIp = IPUtil.getRealIp(request);
			if (StringUtils.isNotBlank(loginIp)) {
				MemberLoginIPLimit limit = memberLoginIPLimitService.getByLoginIp(loginIp);
				if (limit != null) {
					vo.setSuccess(false);
					vo.setMsg("login limited");
					return vo;
				}
			}

			Map data = new HashMap();
			data.put("memberId", memberId);
			vo.setData(data);
		} else {
			vo.setSuccess(false);
		}

		return vo;
	}

	@RequestMapping(value = "/addlimit")
	@ResponseBody
	public CommonVO addLimit(@RequestBody MemberLoginLimitRecord record) {
		CommonVO vo = new CommonVO();
		memberLoginLimitRecordService.save(record);
		memberLoginLimitRecordMsgService.addrecord(record);
		vo.setSuccess(true);
		return vo;
	}

	@RequestMapping(value = "/deletelimits")
	@ResponseBody
	public CommonVO deleteLimits(@RequestBody String[] recordIds) {
		CommonVO vo = new CommonVO();
		memberLoginLimitRecordService.updateMemberLoginLimitRecordEfficientById(recordIds, false);
		memberLoginLimitRecordMsgService.deleterecords(recordIds);
		vo.setSuccess(true);
		return vo;
	}

}
