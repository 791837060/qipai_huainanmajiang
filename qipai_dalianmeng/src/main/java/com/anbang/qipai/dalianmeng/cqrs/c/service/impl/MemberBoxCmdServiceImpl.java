package com.anbang.qipai.dalianmeng.cqrs.c.service.impl;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.baoxiangui.MemberBoxAccountManager;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberBoxCmdService;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.dml.accounting.TextAccountingSummary;
import org.springframework.stereotype.Component;

@Component
public class MemberBoxCmdServiceImpl extends CmdServiceBase implements MemberBoxCmdService {
    @Override
    public AccountingRecord givePowerToMember(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        MemberBoxAccountManager memberBoxAccountManager = singletonEntityRepository.getEntity(MemberBoxAccountManager.class);
        return memberBoxAccountManager.givePowerToMemberByLianmeng(memberId, lianmengId, amount, new TextAccountingSummary(textSummary), currentTime);
    }

    @Override
    public AccountingRecord withdraw(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        MemberBoxAccountManager memberBoxAccountManager = singletonEntityRepository.getEntity(MemberBoxAccountManager.class);
        AccountingRecord rcd = memberBoxAccountManager.withdraw(memberId, lianmengId, amount, new TextAccountingSummary(textSummary), currentTime);
        return rcd;
    }

}
