package com.anbang.qipai.admin.plan.dao.permissiondao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.permission.AdminRefRole;

public interface AdminRefRoleDao {

	void save(AdminRefRole ref);

	List<AdminRefRole> findByAdminId(String adminId);

	void removeByRoleIds(String[] roleIds);

	void removeByAdminIds(String[] adminIds);
}
