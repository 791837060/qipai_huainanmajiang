package com.anbang.qipai.dalianmeng.plan.service;


import com.anbang.qipai.dalianmeng.plan.bean.LianmengApply;
import com.anbang.qipai.dalianmeng.plan.dao.LianmengApplyDao;
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

	public LianmengApply fingLianmengApplyByApplyId(String applyId) {
		return lianmengApplyDao.fingByApplyId(applyId);
	}

	public LianmengApply fingLianmengApplyByAgentIdAndStatus(String agentId, String status) {
		return lianmengApplyDao.fingByAgentIdAndStatus(agentId, status);
	}

	public LianmengApply updateApplyStatus(String applyId, String status) {
		lianmengApplyDao.updateApplyStatus(applyId, status);
		return lianmengApplyDao.fingByApplyId(applyId);
	}

	public List<LianmengApply> listApplyByStatus(String status) {
		return lianmengApplyDao.listApplyByStatus(status);
	}
}
