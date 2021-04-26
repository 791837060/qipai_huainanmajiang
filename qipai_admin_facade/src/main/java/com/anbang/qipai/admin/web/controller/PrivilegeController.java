package com.anbang.qipai.admin.web.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.plan.bean.permission.Privilege;
import com.anbang.qipai.admin.plan.service.permissionservice.PrivilegeService;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.anbang.qipai.admin.web.vo.permissionvo.PrivilegeVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.highto.framework.web.page.ListPage;

/**
 * 管理员权限controller
 * 
 * @author 林少聪 2018.7.9
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

	@Autowired
	private PrivilegeService privilegeService;

	/**
	 * 查询权限
	 * 
	 * @param page
	 * @param size
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value = "/queryprivilege", method = RequestMethod.POST)
	public CommonVO queryPrivilege(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, String privilege) {
		CommonVO vo = new CommonVO();
		ListPage listPage = privilegeService.findPrivilegeByName(page, size, privilege);
		vo.setSuccess(true);
		vo.setMsg("privilegeList");
		vo.setData(listPage);
		return vo;
	}

	/**
	 * 录入权限
	 * 
	 * @param privileges
	 * @return
	 */
	@RequestMapping(value = "/deployprivilege", method = RequestMethod.POST)
	public CommonVO deployPrivilege(@RequestParam(value = "privileges") String privileges) {
		CommonVO vo = new CommonVO();
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Privilege>>() {
		}.getType();
		ArrayList<Privilege> privilegeList = gson.fromJson(privileges, type);
		privilegeService.addPrivileges(privilegeList);
		vo.setSuccess(true);
		vo.setMsg("deploy privilege success");
		return vo;
	}

	/**
	 * 删除权限,包括具有该权限的角色中的权限
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteprivilege", method = RequestMethod.POST)
	public CommonVO deletePrivilege(@RequestParam(value = "id") String[] ids) {
		CommonVO vo = new CommonVO();
		privilegeService.deletePrivilegeByIds(ids);
		vo.setSuccess(true);
		vo.setMsg("delete privilege success");
		return vo;
	}

	/**
	 * 修改权限
	 * 
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value = "/updateprivilege", method = RequestMethod.POST)
	public CommonVO updatePrivilege(Privilege privilege) {
		CommonVO vo = new CommonVO();
		privilegeService.updatePrivilege(privilege);
		vo.setSuccess(true);
		vo.setMsg("update privilege success");
		return vo;
	}

	/**
	 * 查询所有权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryallprivilege", method = RequestMethod.POST)
	public CommonVO queryAllPrivilege(String roleId) {
		CommonVO vo = new CommonVO();
		List<PrivilegeVO> privilegeList = privilegeService.findAllPrivileges(roleId);
		vo.setSuccess(true);
		vo.setMsg("privilegeList");
		vo.setData(privilegeList);
		return vo;
	}
}
