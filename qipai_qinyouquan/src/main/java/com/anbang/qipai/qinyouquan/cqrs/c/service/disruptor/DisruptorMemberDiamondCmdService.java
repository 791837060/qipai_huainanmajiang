package com.anbang.qipai.qinyouquan.cqrs.c.service.disruptor;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.qinyouquan.cqrs.c.service.MemberDiamondCmdService;
import com.anbang.qipai.qinyouquan.cqrs.c.service.impl.MemberDiamondCmdServiceImpl;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "memberDiamondCmdService")
public class DisruptorMemberDiamondCmdService extends DisruptorCmdServiceBase implements MemberDiamondCmdService {

    @Autowired
    private MemberDiamondCmdServiceImpl memberPowerCmdServiceImpl;

    @Override
    public AccountingRecord giveDiamondToMember(String memberId, String lianmengId, Integer amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(MemberDiamondCmdServiceImpl.class.getName(), "giveDiamondToMember", memberId,lianmengId,
                amount, textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            AccountingRecord accountingRecord = memberPowerCmdServiceImpl.giveDiamondToMember(cmd.getParameter(), cmd.getParameter(),
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
    public AccountingRecord withdraw(String memberId, String lianmengId, Integer amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(MemberDiamondCmdServiceImpl.class.getName(), "withdraw", memberId, lianmengId,amount,
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
    public AccountingRecord withdrawAnyway(String memberId, String lianmengId, Integer amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(MemberDiamondCmdServiceImpl.class.getName(), "withdrawAnyway", memberId, lianmengId,amount,
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
