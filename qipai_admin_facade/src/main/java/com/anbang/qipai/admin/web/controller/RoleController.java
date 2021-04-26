package com.anbang.qipai.admin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.plan.bean.permission.Role;
import com.anbang.qipai.admin.plan.service.permissionservice.RoleService;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;

/**
 * 管理员角色controller
 * 
 * @author 林少聪 2018.6.4
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;

	/**
	 * 查询角色
	 * 
	 * @param page
	 * @param size
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/queryrole", method = RequestMethod.POST)
	public CommonVO queryRole(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, String role) {
		CommonVO vo = new CommonVO();
		ListPage listPage = roleService.findRoleByName(page, size, role);
		vo.setSuccess(true);
		vo.setMsg("roleList");
		vo.setData(listPage);
		return vo;
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/addrole", method = RequestMethod.POST)
	public CommonVO addRole(Role role) {
		CommonVO vo = new CommonVO();
		if (role.getRole() == null) {
			vo.setSuccess(false);
			vo.setMsg("role name is null");
			return vo;
		}
		roleService.addRole(role);
		vo.setSuccess(true);
		vo.setMsg("add role success");
		return vo;
	}

	/**
	 * 删除角色,包括具有该角色的管理员中的角色
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleterole", method = RequestMethod.POST)
	public CommonVO deleteRole(@RequestParam(value = "id") String[] ids) {
		CommonVO vo = new CommonVO();
		roleService.deleteRoleByIds(ids);
		vo.setSuccess(true);
		vo.setMsg("delete role success");
		return vo;
	}

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/updaterole", method = RequestMethod.POST)
	public CommonVO updateRole(Role role) {
		CommonVO vo = new CommonVO();
		roleService.updateRole(role);
		vo.setSuccess(true);
		vo.setMsg("update role success");
		return vo;
	}

	/**
	 * 编辑角色的权限
	 * 
	 * @param roleId
	 * @param privilegeIds
	 * @return
	 */
	@RequestMapping(value = "/editprivilege", method = RequestMethod.POST)
	public CommonVO editPrivilege(String roleId, @RequestParam(value = "privilegeId") String[] privilegeIds) {
		CommonVO vo = new CommonVO();
		roleService.editPrivilege(roleId, privilegeIds);
		vo.setSuccess(true);
		vo.setMsg("edit privilege success");
		return vo;
	}

	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryallrole", method = RequestMethod.POST)
	public CommonVO queryAllRole() {
		CommonVO vo = new CommonVO();
		List<Role> roleList = roleService.findAllRoles();
		vo.setSuccess(true);
		vo.setMsg("roleList");
		vo.setData(roleList);
		return vo;
	}
}
