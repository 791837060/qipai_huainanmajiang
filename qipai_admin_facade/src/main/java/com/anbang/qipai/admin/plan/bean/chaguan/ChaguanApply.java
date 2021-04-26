package com.anbang.qipai.admin.plan.bean.chaguan;

import com.anbang.qipai.admin.plan.bean.agents.AgentType;

/**
 * 馆主申请开通茶馆
 * 
 * @author lsc
 *
 */
public class ChaguanApply {
	private String id;
	private String agentId;// 申请人
	private String nickname;// 推广员昵称
	private String headimgurl;// 头像url
	private AgentType agentType;// 推广员类型
	private int inviteMemberNum;// 邀请玩家数量
	private String status;// 申请状态
	private long createTime;// 申请时间

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

	public AgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}

	public int getInviteMemberNum() {
		return inviteMemberNum;
	}

	public void setInviteMemberNum(int inviteMemberNum) {
		this.inviteMemberNum = inviteMemberNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

}
