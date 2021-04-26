package com.anbang.qipai.admin.plan.service.chaguanservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanApply;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanApplyDao;
import com.highto.framework.web.page.ListPage;

@Service
public class ChaguanApplyService {

	@Autowired
	private ChaguanApplyDao chaguanApplyDao;

	public ListPage findByStatus(int page, int size, ChaguanApply apply) {
		int amount = (int) chaguanApplyDao.countByConditions(apply);
		List<ChaguanApply> list = chaguanApplyDao.findByConditions(page, size, apply);
		return new ListPage(list, page, size, amount);
	}

	public void saveChaguanApply(ChaguanApply apply) {
		chaguanApplyDao.save(apply);
	}

	public void chaguanApplyPass(ChaguanApply apply) {
		chaguanApplyDao.save(apply);
	}

	public void chaguanApplyRefuse(ChaguanApply apply) {
		chaguanApplyDao.save(apply);
	}

}
