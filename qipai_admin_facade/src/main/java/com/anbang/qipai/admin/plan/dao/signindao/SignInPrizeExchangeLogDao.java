package com.anbang.qipai.admin.plan.dao.signindao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeExchangeLog;

public interface SignInPrizeExchangeLogDao {

	void addSignInPrizeExchangeLog(SignInPrizeExchangeLog signInPrizeExchangeLog);
	
	List<SignInPrizeExchangeLog> querySignInPrizeExchangeLog(SignInPrizeExchangeLog signInPrizeExchangeLog, Long startTime, Long endTime);
	
	void issueSignInPrize(int id);

	long countUnIssueSignInPrize();

	void updateSignInPrizeExchangeLog(SignInPrizeExchangeLog signInPrizeExchangeLog);

	SignInPrizeExchangeLog findBymemberId(String memberId);
	
}
