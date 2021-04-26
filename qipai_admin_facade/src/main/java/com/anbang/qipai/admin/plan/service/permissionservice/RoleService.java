package com.anbang.qipai.admin.plan.service.permissionservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.permission.Privilege;
import com.anbang.qipai.admin.plan.bean.permission.Role;
import com.anbang.qipai.admin.plan.bean.permission.RoleRefPrivilege;
import com.anbang.qipai.admin.plan.dao.permissiondao.AdminRefRoleDao;
import com.anbang.qipai.admin.plan.dao.permissiondao.PrivilegeDao;
import com.anbang.qipai.admin.plan.dao.permissiondao.RoleDao;
import com.anbang.qipai.admin.plan.dao.permissiondao.RoleRefPrivilegeDao;
import com.highto.framework.web.page.ListPage;

@Service
public class RoleService {

	@Autowired
	private AdminRefRoleDao adminRefRoleDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RoleRefPrivilegeDao roleRefPrivilegeDao;

	@Autowired
	private PrivilegeDao privilegeDao;

	public List<Role> findAllRoles() {
		return roleDao.findAllRoles();
	}

	public void addRole(Role role) {
		roleDao.addRole(role);
	}

	public void deleteRoleByIds(String[] ids) {
		adminRefRoleDao.removeByRoleIds(ids);
		roleDao.deleteRoleByIds(ids);
		roleRefPrivilegeDao.removeByRoleIds(ids);
	}

	public void updateRole(Role role) {
		roleDao.updateRole(role);
	}

	public ListPage findRoleByName(int page, int size, String role) {
		long amount = roleDao.getAmountByName(role);
		List<Role> roleList = roleDao.findRoleByName(page, size, role);
		for (Role r : roleList) {
			List<RoleRefPrivilege> privilegeList = roleRefPrivilegeDao.findByRoleId(r.getId());
			r.setPrivilegeList(privilegeList);
		}
		ListPage listPage = new ListPage(roleList, page, size, (int) amount);
		return listPage;
	}

	public List<RoleRefPrivilege> findPrivilegeByRoleId(String roleId) {
		return roleRefPrivilegeDao.findByRoleId(roleId);
	}

	public void editPrivilege(String roleId, String[] privilegeIds) {
		Role role = roleDao.findRoleById(roleId);
		roleRefPrivilegeDao.removeByRoleIds(new String[] { roleId });
		List<Privilege> privilegeList = privilegeDao.findPrivilegesByIds(privilegeIds);
		for (Privilege privilege : privilegeList) {
			RoleRefPrivilege ref = new RoleRefPrivilege();
			ref.setRoleId(roleId);
			ref.setRole(role.getRole());
			ref.setPrivilegeId(privilege.getId());
			ref.setPrivilege(privilege.getPrivilege());
			ref.setUri(privilege.getUri());
			roleRefPrivilegeDao.save(ref);
		}
	}

}
