package com.anbang.qipai.admin.web.vo.membersvo;

public enum MemberPayType {
	微信支付, 支付宝支付;
	public static String getSummaryText(String text) {
		switch (text) {
		case "wxpay":
			return 微信支付.name();
		case "alipay":
			return 支付宝支付.name();
		default:
			return text;
		}
	}
}
