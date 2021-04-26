package com.anbang.qipai.admin.plan.service.signinservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeExchangeLog;
import com.anbang.qipai.admin.plan.dao.signindao.SignInPrizeExchangeLogDao;

@Service
public class SignInPrizeExchangeLogService {

	@Autowired
	private SignInPrizeExchangeLogDao signInPrizeExchangeLogDao;
	
	/**
	 * 添加签到奖品兑奖纪录
	 * @param signInPrizeExchangeLog
	 */
	public void addSignInPrizeExchangeLog(SignInPrizeExchangeLog signInPrizeExchangeLog) {
		signInPrizeExchangeLogDao.addSignInPrizeExchangeLog(signInPrizeExchangeLog);
	}

	/**
	 * 查询签到奖品兑奖纪录
	 * @return
	 */
	public List<SignInPrizeExchangeLog> querySignInPrizeExchangeLog(SignInPrizeExchangeLog signInPrizeExchangeLog, Long startTime, Long endTime) {
		return signInPrizeExchangeLogDao.querySignInPrizeExchangeLog(signInPrizeExchangeLog,startTime,endTime);
	}

	/**
	 * 发放签到奖品
	 * @param id
	 */
	public void issueSignInPrize(int id) {
		signInPrizeExchangeLogDao.issueSignInPrize(id);
	}
	
	public int countUnIssueSignInPrize() {
		return (int) signInPrizeExchangeLogDao.countUnIssueSignInPrize();
	}

	public void updateSignInPrizeExchangeLog(SignInPrizeExchangeLog signInPrizeExchangeLog) {
		signInPrizeExchangeLogDao.updateSignInPrizeExchangeLog(signInPrizeExchangeLog);
	}

}
