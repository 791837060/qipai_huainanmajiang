package com.anbang.qipai.admin.plan.bean.chaguan;

/**
 * 茶馆
 * 
 * @author lsc
 *
 */
public class ChaguanDbo {
	private String id;// 茶馆id
	private String agentId;// 馆主id
	private String nickname;// 推广员昵称
	private String headimgurl;// 头像url
	private String name;// 茶馆名称
	private String desc;// 茶馆描述
	private int memberNum;// 玩家人数
	private String status;// 茶馆状态
	private long createTime;// 创建时间

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
