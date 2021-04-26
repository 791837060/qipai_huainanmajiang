package com.anbang.qipai.admin.plan.bean.games;

public enum SystemNoticePlace {
	游戏大厅, 游戏房间;
	public static SystemNoticePlace getSystemNoticePlaceByOrdinal(int ordinal) {
		SystemNoticePlace[] values = values();
		return values[ordinal];
	}
}
