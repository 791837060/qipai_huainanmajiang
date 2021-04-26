package com.anbang.qipai.dalianmeng.cqrs.c.service;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.lianmengyushi.AgentHasLianmengYushiAccountAlreadyException;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;

public interface LianmengYushiCmdService {
    String createAccountForAgent(String agentId, Long currentTime) throws AgentHasLianmengYushiAccountAlreadyException;

    AccountingRecord giveYushiToAgent(String agentId, Integer amount, String textSummary, Long currentTime) throws MemberNotFoundException;

    AccountingRecord withdraw(String agentId, Integer amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException;

    boolean createAccountForAgentIsExist(String agentId);
}
