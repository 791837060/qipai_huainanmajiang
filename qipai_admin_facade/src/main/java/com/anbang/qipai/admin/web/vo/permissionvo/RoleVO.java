package com.anbang.qipai.admin.web.vo.permissionvo;

import com.anbang.qipai.admin.plan.bean.permission.Role;

public class RoleVO {
	private Role role;
	private boolean selected;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
