package com.anbang.qipai.qinyouquan.plan.service;


import com.anbang.qipai.qinyouquan.plan.bean.LianmengApply;
import com.anbang.qipai.qinyouquan.plan.dao.LianmengApplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LianmengApplyService {

	@Autowired
	private LianmengApplyDao lianmengApplyDao;

	public void addApply(LianmengApply lianmengApply) {
		lianmengApplyDao.addApply(lianmengApply);
	}

	public LianmengApply findLianmengApplyByApplyId(String applyId) {
		return lianmengApplyDao.findByApplyId(applyId);
	}

	public LianmengApply findLianmengApplyByAgentIdAndStatus(String agentId, String status) {
		return lianmengApplyDao.findByAgentIdAndStatus(agentId, status);
	}

	public LianmengApply updateApplyStatus(String applyId, String status) {
		lianmengApplyDao.updateApplyStatus(applyId, status);
		return lianmengApplyDao.findByApplyId(applyId);
	}

	public List<LianmengApply> listApplyByStatus(String status) {
		return lianmengApplyDao.listApplyByStatus(status);
	}
}
