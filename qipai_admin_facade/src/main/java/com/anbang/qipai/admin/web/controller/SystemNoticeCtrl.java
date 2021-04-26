package com.anbang.qipai.admin.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.cqrs.c.service.AdminAuthService;
import com.anbang.qipai.admin.plan.bean.games.SystemNoticePlace;
import com.anbang.qipai.admin.plan.bean.permission.Admin;
import com.anbang.qipai.admin.plan.service.gameservice.SystemNoticeService;
import com.anbang.qipai.admin.plan.service.permissionservice.AdminService;
import com.anbang.qipai.admin.remote.service.QipaiGameRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;

@CrossOrigin
@RestController
@RequestMapping("/sysnotice")
public class SystemNoticeCtrl {

	@Autowired
	private SystemNoticeService systemNoticeService;

	@Autowired
	private QipaiGameRemoteService qipaiGameRomoteService;

	@Autowired
	private AdminAuthService adminAuthService;

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/querynotice", method = RequestMethod.POST)
	public CommonVO queryNotice(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			String adminName) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		ListPage listPage = systemNoticeService.findByAdminName(page, size, adminName);
		data.put("listPage", listPage);
		vo.setData(data);
		vo.setSuccess(true);
		vo.setMsg("notice list");
		return vo;
	}

	@RequestMapping(value = "/addnotice", method = RequestMethod.POST)
	public CommonVO addNotice(String content, @RequestParam(value = "place") SystemNoticePlace[] places, String token) {
		CommonVO vo = new CommonVO();
		String adminId = adminAuthService.getAdminIdBySessionId(token);
		if (adminId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Admin admin = adminService.findAdminById(adminId);
		CommonRemoteVO rvo = qipaiGameRomoteService.addNotice(content, places, admin.getNickname());
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}

	@RequestMapping(value = "/startnotice", method = RequestMethod.POST)
	public CommonVO startNotice(String id, String token) {
		CommonVO vo = new CommonVO();
		String adminId = adminAuthService.getAdminIdBySessionId(token);
		if (adminId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Admin admin = adminService.findAdminById(adminId);
		CommonRemoteVO rvo = qipaiGameRomoteService.startNotice(id, admin.getNickname());
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}

	@RequestMapping(value = "/stopnotice", method = RequestMethod.POST)
	public CommonVO stopNotice(String id, String token) {
		CommonVO vo = new CommonVO();
		String adminId = adminAuthService.getAdminIdBySessionId(token);
		if (adminId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Admin admin = adminService.findAdminById(adminId);
		CommonRemoteVO rvo = qipaiGameRomoteService.stopNotice(id, admin.getNickname());
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}

	@RequestMapping(value = "/removenotice", method = RequestMethod.POST)
	public CommonVO removeNotice(String id, String token) {
		CommonVO vo = new CommonVO();
		String adminId = adminAuthService.getAdminIdBySessionId(token);
		if (adminId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}

		Admin admin = adminService.findAdminById(adminId);
		CommonRemoteVO rvo = qipaiGameRomoteService.removeNotice(id, admin.getNickname());
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}
}
