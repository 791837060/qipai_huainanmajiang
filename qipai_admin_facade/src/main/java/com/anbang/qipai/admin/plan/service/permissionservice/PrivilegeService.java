package com.anbang.qipai.admin.plan.service.permissionservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.permission.Privilege;
import com.anbang.qipai.admin.plan.bean.permission.RoleRefPrivilege;
import com.anbang.qipai.admin.plan.dao.permissiondao.PrivilegeDao;
import com.anbang.qipai.admin.plan.dao.permissiondao.RoleRefPrivilegeDao;
import com.anbang.qipai.admin.web.vo.permissionvo.PrivilegeVO;
import com.highto.framework.web.page.ListPage;

@Service
public class PrivilegeService {

	@Autowired
	private PrivilegeDao privilegeDao;

	@Autowired
	private RoleRefPrivilegeDao roleRefPrivilegeDao;

	public List<PrivilegeVO> findAllPrivileges(String roleId) {
		List<PrivilegeVO> list = new ArrayList<>();
		List<RoleRefPrivilege> refs = roleRefPrivilegeDao.findByRoleId(roleId);
		List<Privilege> privileges = privilegeDao.findAllPrivileges();
		for (Privilege p : privileges) {
			PrivilegeVO vo = new PrivilegeVO();
			vo.setId(p.getId());
			vo.setPrivilege(p.getPrivilege());
			vo.setUri(p.getUri());
			for (RoleRefPrivilege ref : refs) {
				if (p.getId().equals(ref.getPrivilegeId())) {
					vo.setSelected(true);
				}
			}
			list.add(vo);
		}
		return list;
	}

	public void addPrivileges(List<Privilege> privilegeList) {
		privilegeDao.addPrivileges(privilegeList);
	}

	public void deletePrivilegeByIds(String[] ids) {
		roleRefPrivilegeDao.removeByPrivilegeIds(ids);
		privilegeDao.deletePrivilegeByIds(ids);
	}

	public void updatePrivilege(Privilege privilege) {
		privilegeDao.updatePrivilege(privilege);
	}

	public ListPage findPrivilegeByName(int page, int size, String privilege) {
		long amount = privilegeDao.getAmountByName(privilege);
		List<Privilege> privilegeList = privilegeDao.findPrivilegeByName(page, size, privilege);
		ListPage listPage = new ListPage(privilegeList, page, size, (int) amount);
		return listPage;
	}

}
