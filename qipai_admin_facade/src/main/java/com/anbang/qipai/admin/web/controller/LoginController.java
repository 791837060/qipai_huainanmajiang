package com.anbang.qipai.admin.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.cqrs.c.service.AdminAuthService;
import com.anbang.qipai.admin.plan.bean.permission.Admin;
import com.anbang.qipai.admin.plan.service.permissionservice.AdminService;
import com.anbang.qipai.admin.web.vo.CommonVO;

/**
 * 登录controller
 * 
 * @author 林少聪 2018.5.31
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminAuthService adminAuthService;

	/**
	 * 管理员登录
	 * 
	 */
	@RequestMapping("/login")
	public CommonVO login(@RequestParam(value = "nickname", required = true) String nickname,
			@RequestParam(value = "pass", required = true) String pass) {
		CommonVO vo = new CommonVO();
		Admin admin = adminService.verifyAdmin(nickname, pass);
		if (admin != null) {
			String token = adminAuthService.thirdAuth(admin.getId());
			vo.setSuccess(true);
			vo.setMsg("token");
			Map data = new HashMap();
			admin.setPass(null);
			data.put("admin", admin);
			data.put("token", token);
			vo.setData(data);
			return vo;
		}
		vo.setSuccess(false);
		return vo;
	}

	/**
	 * 管理员登出
	 * 
	 */
	@RequestMapping("/logout")
	public CommonVO logout(String token) {
		CommonVO vo = new CommonVO();
		String adminId = adminAuthService.getAdminIdBySessionId(token);
		if (adminId != null) {
			adminAuthService.removeSessionByAdminId(adminId);
			vo.setMsg("invalid token");
		}
		return vo;
	}

	@RequestMapping("/admin_info")
	public CommonVO info(String token) {
		CommonVO vo = new CommonVO();
		if (token == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		String adminId = adminAuthService.getAdminIdBySessionId(token);
		if (adminId == null) {
			vo.setSuccess(false);
			vo.setMsg("invalid token");
			return vo;
		}
		Admin admin = adminService.findAdminById(adminId);
		Map data = new HashMap();
		admin.setPass(null);
		data.put("admin", admin);
		vo.setData(data);
		return vo;
	}
}
