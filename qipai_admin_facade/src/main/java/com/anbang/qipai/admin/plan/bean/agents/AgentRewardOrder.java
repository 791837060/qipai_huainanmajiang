package com.anbang.qipai.admin.plan.bean.agents;

import java.util.Map;

public class AgentRewardOrder {

	private String id;// 商户订单号
	private String openid;// 用户openid
	private String re_user_name;// 收款用户姓名
	private int amount;// 金额
	private String desc;// 备注
	private String spbill_create_ip;// 服务端Ip地址
	private long createTime;// 发起时间
	private Map returnParams;// 返回参数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getRe_user_name() {
		return re_user_name;
	}

	public void setRe_user_name(String re_user_name) {
		this.re_user_name = re_user_name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Map getReturnParams() {
		return returnParams;
	}

	public void setReturnParams(Map returnParams) {
		this.returnParams = returnParams;
	}

}
