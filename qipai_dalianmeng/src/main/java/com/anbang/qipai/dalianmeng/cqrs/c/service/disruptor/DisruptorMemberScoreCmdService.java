package com.anbang.qipai.dalianmeng.cqrs.c.service.disruptor;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.lianmengyushi.AgentHasLianmengYushiAccountAlreadyException;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.score.MemberHasScoreAccountAlreadyException;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberScoreCmdService;
import com.anbang.qipai.dalianmeng.cqrs.c.service.impl.LianmengYushiCmdServiceImpl;
import com.anbang.qipai.dalianmeng.cqrs.c.service.impl.MemberScoreCmdServiceImpl;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "memberScoreCmdService")
public class DisruptorMemberScoreCmdService extends DisruptorCmdServiceBase implements MemberScoreCmdService {
    @Autowired
    private MemberScoreCmdServiceImpl memberScoreCmdServiceImpl;

    @Override
    public String createAccountForMember(String memberId, String lianmengId, Long currentTime) throws MemberHasScoreAccountAlreadyException {
        CommonCommand cmd = new CommonCommand(MemberScoreCmdServiceImpl.class.getName(), "createAccountForMember", memberId,lianmengId,
                currentTime);
        DeferredResult<String> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            String accountId = memberScoreCmdServiceImpl.createAccountForMember(cmd.getParameter(),cmd.getParameter(),
                    cmd.getParameter());
            return accountId;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            if (e instanceof MemberHasScoreAccountAlreadyException) {
                throw (MemberHasScoreAccountAlreadyException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public AccountingRecord giveScoreToMember(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(MemberScoreCmdServiceImpl.class.getName(), "giveScoreToMember", memberId, lianmengId, amount,
                textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            AccountingRecord accountingRecord = memberScoreCmdServiceImpl.giveScoreToMember(cmd.getParameter(), cmd.getParameter(),
                    cmd.getParameter(), cmd.getParameter(), cmd.getParameter());
            return accountingRecord;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            if (e instanceof MemberNotFoundException) {
                throw (MemberNotFoundException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public AccountingRecord withdraw(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(MemberScoreCmdServiceImpl.class.getName(), "withdraw", memberId, lianmengId, amount,
                textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            AccountingRecord accountingRecord = memberScoreCmdServiceImpl.withdraw(cmd.getParameter(), cmd.getParameter(),
                    cmd.getParameter(), cmd.getParameter(), cmd.getParameter());
            return accountingRecord;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            if (e instanceof InsufficientBalanceException) {
                throw (InsufficientBalanceException) e;
            } else if (e instanceof MemberNotFoundException) {
                throw (MemberNotFoundException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }
}
