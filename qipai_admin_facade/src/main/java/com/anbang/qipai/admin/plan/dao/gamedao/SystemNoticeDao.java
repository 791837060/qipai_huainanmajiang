package com.anbang.qipai.admin.plan.dao.gamedao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.games.SystemNotice;

public interface SystemNoticeDao {

	void save(SystemNotice notice);

	void addSystemNotices(List<SystemNotice> notices);

	void updateSystemNoticeValid(String noticeId, String adminName, boolean valid);

	void updateSystemNoticeState(String noticeId, String adminName, String state);

	List<SystemNotice> findByAdminName(int page, int size, String adminName);

	long countByAdminName(String adminName);

	SystemNotice findById(String noticeId);
}
