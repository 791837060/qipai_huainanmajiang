package com.anbang.qipai.qinyouquan.cqrs.c.service;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.lianmengdiamond.MengzhuHasLianmengDiamondAccountAlreadyException;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;

public interface LianmengDiamondCmdService {
    String createAccountForMengzhu(String mengzhuId, Long currentTime) throws MengzhuHasLianmengDiamondAccountAlreadyException;

    AccountingRecord giveDiamondToMengzhu(String mengzhuId, Integer amount, String textSummary, Long currentTime) throws MemberNotFoundException;

    AccountingRecord withdraw(String mengzhuId, Integer amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException;

    boolean createAccountForAgentIsExist(String agentId);
}
