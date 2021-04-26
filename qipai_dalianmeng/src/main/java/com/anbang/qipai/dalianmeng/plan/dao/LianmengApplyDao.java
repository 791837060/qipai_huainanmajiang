package com.anbang.qipai.dalianmeng.plan.dao;



import com.anbang.qipai.dalianmeng.plan.bean.LianmengApply;

import java.util.List;

public interface LianmengApplyDao {

	void addApply(LianmengApply lianmengApply);

	LianmengApply fingByApplyId(String applyId);

	LianmengApply fingByAgentIdAndStatus(String agentId, String status);

	void updateApplyStatus(String applyId, String status);

	List<LianmengApply> listApplyByStatus(String status);
}
