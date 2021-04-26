package com.anbang.qipai.dalianmeng.cqrs.c.service.impl;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.score.MemberHasScoreAccountAlreadyException;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.score.MemberScoreAccountManager;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberScoreCmdService;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.dml.accounting.TextAccountingSummary;
import org.springframework.stereotype.Component;

@Component
public class MemberScoreCmdServiceImpl extends CmdServiceBase implements MemberScoreCmdService {
    @Override
    public String createAccountForMember(String memberId, String lianmengId,Long currentTime) throws MemberHasScoreAccountAlreadyException {
        MemberScoreAccountManager memberScoreAccountManager = singletonEntityRepository
                .getEntity(MemberScoreAccountManager.class);
        return memberScoreAccountManager.createScoreAccountForNewMember(memberId,lianmengId);
    }

    @Override
    public AccountingRecord giveScoreToMember(String memberId,  String lianmengId,Double amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        MemberScoreAccountManager memberScoreAccountManager = singletonEntityRepository
                .getEntity(MemberScoreAccountManager.class);
        return memberScoreAccountManager.giveScoreToMemberByLianmeng(memberId, lianmengId,amount, new TextAccountingSummary(textSummary),
                currentTime);
    }

    @Override
    public AccountingRecord withdraw(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        MemberScoreAccountManager memberScoreAccountManager = singletonEntityRepository
                .getEntity(MemberScoreAccountManager.class);
        return memberScoreAccountManager.withdraw(memberId, lianmengId,amount, new TextAccountingSummary(textSummary),
                currentTime);
    }
}
