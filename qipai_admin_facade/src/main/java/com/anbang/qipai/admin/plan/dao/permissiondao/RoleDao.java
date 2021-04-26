package com.anbang.qipai.admin.plan.dao.permissiondao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.permission.Privilege;
import com.anbang.qipai.admin.plan.bean.permission.Role;

/**
 * 角色Dao
 * 
 * @author 林少聪 2018.5.31
 *
 */
public interface RoleDao {

	List<Role> findAllRoles();

	Role findRoleById(String roleId);

	void updatePrivilegeList(String roleId, List<Privilege> privilegeList);
	
	List<Role> findRolesByIds(String[] roleIds);

	List<Role> findRoleByName(int page, int size, String role);

	void addRole(Role role);

	void deleteRoleByIds(String[] ids);

	void updateRole(Role role);

	long getAmountByName(String role);

	void deletePrivilegeByPrivilegeId(String[] privilegeIds);
}
