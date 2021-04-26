package com.anbang.qipai.admin.plan.service.gameservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.games.SystemNotice;
import com.anbang.qipai.admin.plan.dao.gamedao.SystemNoticeDao;
import com.highto.framework.web.page.ListPage;

@Service
public class SystemNoticeService {

	@Autowired
	private SystemNoticeDao systemNoticeDao;

	public void save(SystemNotice notice) {
		systemNoticeDao.save(notice);
	}

	public void addSystemNotices(List<SystemNotice> list) {
		systemNoticeDao.addSystemNotices(list);
	}

	public SystemNotice findById(String noticeId) {
		return systemNoticeDao.findById(noticeId);
	}

	public void updateSystemNoticeState(String noticeId, String adminName, String state) {
		systemNoticeDao.updateSystemNoticeState(noticeId, adminName, state);
	}

	public void updateSystemNoticeValid(String noticeId, String adminName, boolean valid) {
		systemNoticeDao.updateSystemNoticeValid(noticeId, adminName, valid);
	}

	public ListPage findByAdminName(int page, int size, String adminName) {
		long amount = systemNoticeDao.countByAdminName(adminName);
		List<SystemNotice> noticeList = systemNoticeDao.findByAdminName(page, size, adminName);
		ListPage listPage = new ListPage(noticeList, page, size, (int) amount);
		return listPage;
	}

}
