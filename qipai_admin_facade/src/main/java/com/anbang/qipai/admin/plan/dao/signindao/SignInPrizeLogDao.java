package com.anbang.qipai.admin.plan.dao.signindao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeLog;

public interface SignInPrizeLogDao {

	void addSignInPrizeLog(SignInPrizeLog signInPrizeLog) ;
	
	List<SignInPrizeLog> querySignInPrizeLog(SignInPrizeLog signInPrizeLog, Long startTime, Long endTime,int page ,int size);

	int countSignInPrizeLog(SignInPrizeLog signInPrizeLog, Long startTime, Long endTime);
	
}
