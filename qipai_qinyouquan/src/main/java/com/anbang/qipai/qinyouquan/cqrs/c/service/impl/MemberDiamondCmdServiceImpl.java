package com.anbang.qipai.qinyouquan.cqrs.c.service.impl;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.memberdiamond.MemberDiamondAccountManager;
import com.anbang.qipai.qinyouquan.cqrs.c.service.MemberDiamondCmdService;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.dml.accounting.TextAccountingSummary;
import org.springframework.stereotype.Component;

@Component
public class MemberDiamondCmdServiceImpl extends CmdServiceBase implements MemberDiamondCmdService {
    @Override
    public AccountingRecord giveDiamondToMember(String memberId, String lianmengId, Integer amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        MemberDiamondAccountManager memberDiamondAccountManager = singletonEntityRepository
                .getEntity(MemberDiamondAccountManager.class);
        return memberDiamondAccountManager.givePowerToMemberByLianmeng(memberId, lianmengId, amount, new TextAccountingSummary(textSummary),
                currentTime);
    }

    @Override
    public AccountingRecord withdraw(String memberId, String lianmengId, Integer amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        MemberDiamondAccountManager memberDiamondAccountManager = singletonEntityRepository
                .getEntity(MemberDiamondAccountManager.class);
        AccountingRecord rcd = memberDiamondAccountManager.withdraw(memberId, lianmengId, amount,
                new TextAccountingSummary(textSummary), currentTime);
        return rcd;
    }

    @Override
    public AccountingRecord withdrawAnyway(String memberId, String lianmengId, Integer amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        MemberDiamondAccountManager memberDiamondAccountManager = singletonEntityRepository
                .getEntity(MemberDiamondAccountManager.class);
        AccountingRecord rcd = memberDiamondAccountManager.withdrawAnyway(memberId, lianmengId, amount,
                new TextAccountingSummary(textSummary), currentTime);
        return rcd;
    }
}
