package com.anbang.qipai.admin.plan.bean.members;

public class MemberOperationRecord {
	private String id;
	private String memberId;
	private String memberName;
	private String desc;// 操作描述
	private String param;// 参数
	private long operationTime;// 操作时间
	private String operator;// 操作人

	public MemberOperationRecord() {

	}

	public MemberOperationRecord(MemberDbo member) {
		this.memberId = member.getId();
		this.memberName = member.getNickname();
		this.operationTime = System.currentTimeMillis();
	}

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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public long getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(long operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
