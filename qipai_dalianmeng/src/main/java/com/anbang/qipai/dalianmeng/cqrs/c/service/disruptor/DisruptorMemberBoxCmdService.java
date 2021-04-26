package com.anbang.qipai.dalianmeng.cqrs.c.service.disruptor;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberBoxCmdService;
import com.anbang.qipai.dalianmeng.cqrs.c.service.impl.MemberBoxCmdServiceImpl;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "memberBoxCmdService")
public class DisruptorMemberBoxCmdService extends DisruptorCmdServiceBase implements MemberBoxCmdService {

    @Autowired
    private MemberBoxCmdServiceImpl memberBoxCmdServiceImpl;

    @Override
    public AccountingRecord givePowerToMember(String memberId, String lianmengId, Double amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(MemberBoxCmdServiceImpl.class.getName(), "givePowerToMember", memberId,lianmengId, amount, textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            return memberBoxCmdServiceImpl.givePowerToMember(cmd.getParameter(), cmd.getParameter(), cmd.getParameter(), cmd.getParameter(), cmd.getParameter());
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
        CommonCommand cmd = new CommonCommand(MemberBoxCmdServiceImpl.class.getName(), "withdraw", memberId,lianmengId, amount, textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            return memberBoxCmdServiceImpl.withdraw(cmd.getParameter(), cmd.getParameter(), cmd.getParameter(), cmd.getParameter(), cmd.getParameter());
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
