package com.anbang.qipai.admin.plan.bean.permission;

import java.util.List;

public class Admin {
	private String id;						// 管理员id
	private String nickname;				// 管理员昵稱
	private String pass;					// 管理员密码
	private String user;					// 管理员真实姓名
	private String idCard;					// 管理员身份证
	private long createTime;				// 创建时间
	private List<AdminRefRole> roleList;	// 角色列表
	private int diamondLimit;				//钻石额度

	private int jadeLimit;					//玉石额度

	public int getDiamondLimit() {
		return diamondLimit;
	}

	public void setDiamondLimit(int diamondLimit) {
		this.diamondLimit = diamondLimit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public List<AdminRefRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<AdminRefRole> roleList) {
		this.roleList = roleList;
	}

	public int getJadeLimit() {
		return jadeLimit;
	}

	public void setJadeLimit(int jadeLimit) {
		this.jadeLimit = jadeLimit;
	}
}
