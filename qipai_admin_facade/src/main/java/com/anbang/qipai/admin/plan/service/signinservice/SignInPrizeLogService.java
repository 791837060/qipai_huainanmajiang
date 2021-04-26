package com.anbang.qipai.admin.plan.service.signinservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeExchangeLog;
import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeLog;
import com.anbang.qipai.admin.plan.dao.signindao.SignInPrizeExchangeLogDao;
import com.anbang.qipai.admin.plan.dao.signindao.SignInPrizeLogDao;

@Service
public class SignInPrizeLogService {

    @Autowired
    private SignInPrizeLogDao signInPrizeLogDao;

    @Autowired
    private SignInPrizeExchangeLogDao signInPrizeExchangeLogDao;

    /**
     * 添加中奖纪录
     *
     * @param signInPrizeLog
     */
    public void addSignInPrizeLog(SignInPrizeLog signInPrizeLog) {
        signInPrizeLogDao.addSignInPrizeLog(signInPrizeLog);
//		String type = signInPrizeLog.getType();
//		if (type.equals("实物") || type.equals("红包")||type.equals("话费")) {
//			SignInPrizeExchangeLog signInPrizeExchangeLog = new SignInPrizeExchangeLog();
//			//signInPrizeExchangeLog.setSignInPrizeLog(signInPrizeLog);
//			signInPrizeExchangeLogDao.addSignInPrizeExchangeLog(signInPrizeExchangeLog);
//		}
    }

    /**
     * 查询中奖纪录
     *
     * @return
     */
    public List<SignInPrizeLog> querySignInPrizeLog(SignInPrizeLog signInPrizeLog, Long startTime, Long endTime,
                                                    int page, int size) {
        return signInPrizeLogDao.querySignInPrizeLog(signInPrizeLog, startTime, endTime, page, size);
    }


    public int countSignInPrizeLog(SignInPrizeLog signInPrizeLog, Long startTime, Long endTime) {
        return signInPrizeLogDao.countSignInPrizeLog(signInPrizeLog, startTime, endTime);
    }
}
