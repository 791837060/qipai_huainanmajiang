package com.anbang.qipai.admin.plan.dao.permissiondao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.permission.Privilege;

/**
 * 权限Dao
 * 
 * @author 林少聪 2018.5.31
 *
 */
public interface PrivilegeDao {

	long getAmountByName(String privilege);

	List<Privilege> findPrivilegeByName(int page, int size, String privilege);

	List<Privilege> findPrivilegesByIds(String[] privilegeIds);

	void addPrivileges(List<Privilege> privilegeList);

	List<Privilege> findPrivilegeByUri(List<String> uriList);

	List<Privilege> findAllPrivileges();

	void deletePrivilegeByIds(String[] ids);

	void updatePrivilege(Privilege privilege);
}
