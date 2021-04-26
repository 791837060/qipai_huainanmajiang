package com.anbang.qipai.admin.plan.bean.permission;

public class Privilege {

	private String id;// 权限id
	private String privilege;// 权限名称
	private String uri;// 拦截URI

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
