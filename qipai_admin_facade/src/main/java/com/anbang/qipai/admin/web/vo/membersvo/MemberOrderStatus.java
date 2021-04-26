package com.anbang.qipai.admin.web.vo.membersvo;

public enum MemberOrderStatus {
	支付成功, 转入退款, 未支付, 已关闭, 已撤销, 用户支付中, 支付失败, 交易创建等待买家付款, 未付款交易超时关闭或支付完成后全额退款, 交易支付成功, 交易结束不可退款;

	public static String getSummaryText(String text) {
		if (text == null) {
			return null;
		}
		switch (text) {
		case "SUCCESS":
			return 支付成功.name();
		case "REFUND":
			return 转入退款.name();
		case "NOTPAY":
			return 未支付.name();
		case "CLOSED":
			return 已关闭.name();
		case "REVOKED":
			return 已撤销.name();
		case "USERPAYING":
			return 用户支付中.name();
		case "PAYERROR":
			return 支付失败.name();
		case "WAIT_BUYER_PAY":
			return 交易创建等待买家付款.name();
		case "TRADE_CLOSED":
			return 未付款交易超时关闭或支付完成后全额退款.name();
		case "TRADE_SUCCESS":
			return 交易支付成功.name();
		case "TRADE_FINISHED":
			return 交易结束不可退款.name();
		default:
			return text;
		}
	}
}
