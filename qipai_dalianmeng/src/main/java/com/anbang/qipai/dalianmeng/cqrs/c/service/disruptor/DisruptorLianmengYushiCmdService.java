package com.anbang.qipai.dalianmeng.cqrs.c.service.disruptor;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.lianmengyushi.AgentHasLianmengYushiAccountAlreadyException;
import com.anbang.qipai.dalianmeng.cqrs.c.service.LianmengYushiCmdService;
import com.anbang.qipai.dalianmeng.cqrs.c.service.impl.LianmengYushiCmdServiceImpl;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "lianmengYushiCmdService")
public class DisruptorLianmengYushiCmdService extends DisruptorCmdServiceBase implements LianmengYushiCmdService {
    @Autowired
    private LianmengYushiCmdServiceImpl lianmengYushiCmdServiceImpl;

    @Override
    public String createAccountForAgent(String agentId, Long currentTime) throws AgentHasLianmengYushiAccountAlreadyException {
        CommonCommand cmd = new CommonCommand(LianmengYushiCmdServiceImpl.class.getName(), "createAccountForAgent", agentId,
                currentTime);
        DeferredResult<String> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            String accountId = lianmengYushiCmdServiceImpl.createAccountForAgent(cmd.getParameter(),
                    cmd.getParameter());
            return accountId;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            if (e instanceof AgentHasLianmengYushiAccountAlreadyException) {
                throw (AgentHasLianmengYushiAccountAlreadyException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public AccountingRecord giveYushiToAgent(String agentId, Integer amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(LianmengYushiCmdServiceImpl.class.getName(), "giveYushiToAgent", agentId, amount,
                textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            AccountingRecord accountingRecord = lianmengYushiCmdServiceImpl.giveYushiToAgent(cmd.getParameter(),
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
    public AccountingRecord withdraw(String agentId, Integer amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(LianmengYushiCmdServiceImpl.class.getName(), "withdraw", agentId, amount,
                textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            AccountingRecord accountingRecord = lianmengYushiCmdServiceImpl.withdraw(cmd.getParameter(),
                    cmd.getParameter(), cmd.getParameter(), cmd.getParameter());
            return accountingRecord;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            if (e instanceof MemberNotFoundException) {
                throw (MemberNotFoundException) e;
            } else if (e instanceof InsufficientBalanceException) {
                throw (InsufficientBalanceException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean createAccountForAgentIsExist(String agentId) {
        CommonCommand cmd = new CommonCommand(LianmengYushiCmdServiceImpl.class.getName(), "createAccountForAgentIsExist", agentId);
        DeferredResult<Boolean> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            return lianmengYushiCmdServiceImpl.createAccountForAgentIsExist(cmd.getParameter());
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
