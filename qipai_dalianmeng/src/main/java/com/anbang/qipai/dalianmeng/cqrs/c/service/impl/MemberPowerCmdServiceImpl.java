package com.anbang.qipai.dalianmeng.cqrs.c.service.impl;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.power.MemberPowerAccountManager;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberPowerCmdService;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.dml.accounting.TextAccountingSummary;
import org.springframework.stereotype.Component;

@Component
public class MemberPowerCmdServiceImpl extends CmdServiceBase implements MemberPowerCmdService {
    @Override
    public AccountingRecord givePowerToMember(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        MemberPowerAccountManager memberPowerAccountManager = singletonEntityRepository
                .getEntity(MemberPowerAccountManager.class);
        return memberPowerAccountManager.givePowerToMemberByLianmeng(memberId, lianmengId, amount, new TextAccountingSummary(textSummary),
                currentTime);
    }

    @Override
    public AccountingRecord withdraw(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        MemberPowerAccountManager memberPowerAccountManager = singletonEntityRepository
                .getEntity(MemberPowerAccountManager.class);
        AccountingRecord rcd = memberPowerAccountManager.withdraw(memberId, lianmengId, amount,
                new TextAccountingSummary(textSummary), currentTime);
        return rcd;
    }

    @Override
    public AccountingRecord withdrawAnyway(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        MemberPowerAccountManager memberPowerAccountManager = singletonEntityRepository
                .getEntity(MemberPowerAccountManager.class);
        AccountingRecord rcd = memberPowerAccountManager.withdrawAnyway(memberId, lianmengId, amount,
                new TextAccountingSummary(textSummary), currentTime);
        return rcd;
    }
}
