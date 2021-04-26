package com.anbang.qipai.admin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.plan.bean.permission.Admin;
import com.anbang.qipai.admin.plan.service.permissionservice.AdminService;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;

/**
 * 管理员controller
 * 
 * @author 林少聪 2018.5.31
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/queryadmin", method = RequestMethod.POST)
	public CommonVO queryAdmin(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, String nickname) {
		CommonVO vo = new CommonVO();
		ListPage listPage = adminService.findAdminByNickname(page, size, nickname);
		vo.setSuccess(true);
		vo.setMsg("adminList");
		vo.setData(listPage);
		return vo;
	}

	@RequestMapping(value = "/addadmin", method = RequestMethod.POST)
	public CommonVO addAdmin(Admin admin) {
		CommonVO vo = new CommonVO();
		if (admin.getNickname() == null || admin.getPass() == null || admin.getUser() == null
				|| admin.getIdCard() == null) {
			vo.setSuccess(false);
			vo.setMsg("at least one param is null");
			return vo;
		}
		adminService.addAdmin(admin);
		vo.setSuccess(true);
		vo.setMsg("add admin success");
		return vo;
	}

	@RequestMapping(value = "/deleteadmin", method = RequestMethod.POST)
	public CommonVO deleteAdmin(@RequestParam(value = "id") String[] ids) {
		CommonVO vo = new CommonVO();
		adminService.deleteAdminByIds(ids);
		vo.setSuccess(true);
		vo.setMsg("delete admins success");
		return vo;
	}

	@RequestMapping(value = "/repass", method = RequestMethod.POST)
	public CommonVO repass(@RequestParam(required = true) String id, @RequestParam(required = true) String pass) {
		CommonVO vo = new CommonVO();
		adminService.updateAdminPass(id, pass);
		vo.setSuccess(true);
		vo.setMsg("repass success");
		return vo;
	}

	@RequestMapping(value = "/editrole", method = RequestMethod.POST)
	public CommonVO editRole(String adminId, @RequestParam(value = "roleId") String[] roleIds) {
		CommonVO vo = new CommonVO();
		adminService.updateRole(adminId, roleIds);
		vo.setSuccess(true);
		vo.setMsg("edit role success");
		return vo;
	}
}
