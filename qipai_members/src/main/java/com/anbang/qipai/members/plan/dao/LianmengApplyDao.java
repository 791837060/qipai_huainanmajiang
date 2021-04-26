package com.anbang.qipai.members.plan.dao;





import com.anbang.qipai.members.plan.bean.LianmengApply;

import java.util.List;

public interface LianmengApplyDao {

	void addApply(LianmengApply lianmengApply);

	LianmengApply findByApplyId(String applyId);

	LianmengApply findByMemberIdAndStatus(String memberId, String status);

	void updateApplyStatus(String applyId, String status);

	List<LianmengApply> listApplyByStatus(String status);

    LianmengApply findByMemberIdAndStatus(String memberId );

    void deleteByMemberIdAndStatus(String memberId );
}
