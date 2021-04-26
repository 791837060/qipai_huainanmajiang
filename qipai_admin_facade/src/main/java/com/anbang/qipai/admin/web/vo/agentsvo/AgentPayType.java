package com.anbang.qipai.admin.web.vo.agentsvo;

public enum AgentPayType {
	微信支付, 积分兑换;
	public static String getSummaryText(String text) {
		if (text == null) {
			return null;
		}
		switch (text) {
		case "wxpay":
			return 微信支付.name();
		case "scorepay":
			return 积分兑换.name();
		default:
			return text;
		}
	}
}
