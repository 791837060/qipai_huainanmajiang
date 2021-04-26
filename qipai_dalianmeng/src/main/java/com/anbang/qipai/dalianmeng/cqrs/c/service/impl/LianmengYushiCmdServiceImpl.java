package com.anbang.qipai.dalianmeng.cqrs.c.service.impl;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.lianmengyushi.AgentHasLianmengYushiAccountAlreadyException;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.lianmengyushi.LianmengYushiAccountManager;
import com.anbang.qipai.dalianmeng.cqrs.c.service.LianmengYushiCmdService;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.dml.accounting.TextAccountingSummary;
import org.springframework.stereotype.Component;

@Component
public class LianmengYushiCmdServiceImpl extends CmdServiceBase implements LianmengYushiCmdService {
    @Override
    public String createAccountForAgent(String agentId, Long currentTime) throws AgentHasLianmengYushiAccountAlreadyException {
        LianmengYushiAccountManager lianmengYushiAccountManager = singletonEntityRepository.getEntity(LianmengYushiAccountManager.class);
        return lianmengYushiAccountManager.createYushiAccountForAgent(agentId);
    }

    @Override
    public AccountingRecord giveYushiToAgent(String agentId, Integer amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        LianmengYushiAccountManager lianmengYushiAccountManager = singletonEntityRepository.getEntity(LianmengYushiAccountManager.class);
        return lianmengYushiAccountManager.giveYushiToAgent(agentId, amount, new TextAccountingSummary(textSummary),
                currentTime);
    }

    @Override
    public AccountingRecord withdraw(String agentId, Integer amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        LianmengYushiAccountManager lianmengYushiAccountManager = singletonEntityRepository.getEntity(LianmengYushiAccountManager.class);
        return lianmengYushiAccountManager.withdraw(agentId, amount, new TextAccountingSummary(textSummary), currentTime);
    }

    @Override
    public boolean createAccountForAgentIsExist(String agentId) {
        LianmengYushiAccountManager lianmengYushiAccountManager = singletonEntityRepository.getEntity(LianmengYushiAccountManager.class);
        return lianmengYushiAccountManager.createAccountForAgentIsExist(agentId);
    }

}
