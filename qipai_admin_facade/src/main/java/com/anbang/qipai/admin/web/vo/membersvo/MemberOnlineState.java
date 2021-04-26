package com.anbang.qipai.admin.web.vo.membersvo;

public enum MemberOnlineState {
	在线, 下线;

	public static String getSummaryText(String text) {
		if (text == null) {
			return null;
		}
		switch (text) {
		case "online":
			return 在线.name();
		case "offline":
			return 下线.name();
		default:
			return text;
		}
	}
}
