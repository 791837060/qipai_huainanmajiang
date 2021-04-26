package com.anbang.qipai.dalianmeng.cqrs.c.service;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;

public interface MemberBoxCmdService {

    AccountingRecord givePowerToMember(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws MemberNotFoundException;

    AccountingRecord withdraw(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException;
}
