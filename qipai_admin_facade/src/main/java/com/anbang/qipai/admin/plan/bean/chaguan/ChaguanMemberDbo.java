package com.anbang.qipai.admin.plan.bean.chaguan;

/**
 * 茶馆成员
 * 
 * @author lsc
 *
 */
public class ChaguanMemberDbo {
	private String id;
	private String memberId;// 玩家id
	private String headimgurl;// 玩家头像
	private String memberNickname;// 玩家昵称
	private String memberDesc;// 玩家备注
	private String chaguanId;// 茶馆id
	private String chaguanName;// 茶馆名称
	private String chaguanDesc;// 茶馆描述
	private String agentId;// 馆主id
	private String agentNickname;// 推广员昵称
	private String agentHeadimgurl;// 推广员头像url
	private String onlineStatus;// 在线状态
	private int chaguanYushi;// 茶馆玉石
	private String payType;// 支付方式
	private long joinTime;// 加入时间
	private boolean remove;// 成员是否被移出茶馆

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getMemberNickname() {
		return memberNickname;
	}

	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}

	public String getMemberDesc() {
		return memberDesc;
	}

	public void setMemberDesc(String memberDesc) {
		this.memberDesc = memberDesc;
	}

	public String getChaguanId() {
		return chaguanId;
	}

	public void setChaguanId(String chaguanId) {
		this.chaguanId = chaguanId;
	}

	public String getChaguanName() {
		return chaguanName;
	}

	public void setChaguanName(String chaguanName) {
		this.chaguanName = chaguanName;
	}

	public String getChaguanDesc() {
		return chaguanDesc;
	}

	public void setChaguanDesc(String chaguanDesc) {
		this.chaguanDesc = chaguanDesc;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentNickname() {
		return agentNickname;
	}

	public void setAgentNickname(String agentNickname) {
		this.agentNickname = agentNickname;
	}

	public String getAgentHeadimgurl() {
		return agentHeadimgurl;
	}

	public void setAgentHeadimgurl(String agentHeadimgurl) {
		this.agentHeadimgurl = agentHeadimgurl;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public int getChaguanYushi() {
		return chaguanYushi;
	}

	public void setChaguanYushi(int chaguanYushi) {
		this.chaguanYushi = chaguanYushi;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}
}
