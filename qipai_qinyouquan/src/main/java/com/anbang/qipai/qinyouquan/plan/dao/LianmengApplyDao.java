package com.anbang.qipai.qinyouquan.plan.dao;



import com.anbang.qipai.qinyouquan.plan.bean.LianmengApply;

import java.util.List;

public interface LianmengApplyDao {

	void addApply(LianmengApply lianmengApply);

	LianmengApply findByApplyId(String applyId);

	LianmengApply findByAgentIdAndStatus(String agentId, String status);

	void updateApplyStatus(String applyId, String status);

	List<LianmengApply> listApplyByStatus(String status);
}
