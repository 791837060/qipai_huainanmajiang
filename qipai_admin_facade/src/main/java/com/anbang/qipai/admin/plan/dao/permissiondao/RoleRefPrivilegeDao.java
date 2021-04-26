package com.anbang.qipai.admin.plan.dao.permissiondao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.permission.RoleRefPrivilege;

public interface RoleRefPrivilegeDao {
	void save(RoleRefPrivilege ref);

	List<RoleRefPrivilege> findByRoleId(String roleId);

	void removeByPrivilegeIds(String[] privilegeIds);

	void removeByRoleIds(String[] roleIds);
}
