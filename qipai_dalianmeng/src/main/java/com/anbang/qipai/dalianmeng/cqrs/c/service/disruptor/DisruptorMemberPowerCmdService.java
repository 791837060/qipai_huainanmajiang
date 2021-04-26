package com.anbang.qipai.dalianmeng.cqrs.c.service.disruptor;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberPowerCmdService;
import com.anbang.qipai.dalianmeng.cqrs.c.service.impl.MemberPowerCmdServiceImpl;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "memberPowerCmdService")
public class DisruptorMemberPowerCmdService extends DisruptorCmdServiceBase implements MemberPowerCmdService {

    @Autowired
    private MemberPowerCmdServiceImpl memberPowerCmdServiceImpl;

    @Override
    public AccountingRecord givePowerToMember(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(MemberPowerCmdServiceImpl.class.getName(), "givePowerToMember", memberId,lianmengId,
                amount, textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            AccountingRecord accountingRecord = memberPowerCmdServiceImpl.givePowerToMember(cmd.getParameter(), cmd.getParameter(),
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
        CommonCommand cmd = new CommonCommand(MemberPowerCmdServiceImpl.class.getName(), "withdraw", memberId, lianmengId,amount,
                textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            AccountingRecord accountingRecord = memberPowerCmdServiceImpl.withdraw(cmd.getParameter(), cmd.getParameter(),
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

    @Override
    public AccountingRecord withdrawAnyway(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(MemberPowerCmdServiceImpl.class.getName(), "withdrawAnyway", memberId, lianmengId,amount,
                textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            AccountingRecord accountingRecord = memberPowerCmdServiceImpl.withdrawAnyway(cmd.getParameter(), cmd.getParameter(),
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
