package com.anbang.qipai.admin.web.vo.permissionvo;

import java.util.Date;

import com.anbang.qipai.admin.plan.bean.permission.Admin;

public class UserVO {
	private Admin admin;
	private Date loginTime;

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
