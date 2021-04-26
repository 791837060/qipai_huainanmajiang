package com.anbang.qipai.qinyouquan.cqrs.c.service;


import com.anbang.qipai.qinyouquan.cqrs.c.domain.MemberNotFoundException;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;

public interface MemberDiamondCmdService {
    AccountingRecord giveDiamondToMember(String memberId, String lianmengId, Integer amount, String textSummary, Long currentTime)
            throws MemberNotFoundException;

    AccountingRecord withdraw(String memberId, String lianmengId, Integer amount, String textSummary, Long currentTime)
            throws InsufficientBalanceException, MemberNotFoundException;

    AccountingRecord withdrawAnyway(String memberId, String lianmengId, Integer amount, String textSummary, Long currentTime)
            throws InsufficientBalanceException, MemberNotFoundException;
}
