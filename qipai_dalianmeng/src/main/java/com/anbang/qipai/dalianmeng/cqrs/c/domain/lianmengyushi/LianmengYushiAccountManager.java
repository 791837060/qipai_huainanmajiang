package com.anbang.qipai.dalianmeng.cqrs.c.domain.lianmengyushi;


import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.dml.accounting.Account;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.AccountingSummary;
import com.dml.accounting.InsufficientBalanceException;

import java.util.HashMap;
import java.util.Map;

/**
 * 成员联盟玉石账户管理，用于玩家充值
 *
 * @author lsc
 */
public class LianmengYushiAccountManager {

    private Map<String, Account> idAccountMap = new HashMap<>();

    private Map<String, String> agentIdAccountIdMap = new HashMap<>();

    /**
     * 创建联盟玉石账户
     *
     * @param agentId 成员id
     * @return
     * @throws AgentHasLianmengYushiAccountAlreadyException
     */
    public String createYushiAccountForAgent(String agentId)
            throws AgentHasLianmengYushiAccountAlreadyException {
        if (idAccountMap.containsKey(agentId + "_lmyushi_wallet")) {
            throw new AgentHasLianmengYushiAccountAlreadyException();
        }
        LianmengYushiAccountOwner mao = new LianmengYushiAccountOwner();
        mao.setAgentId(agentId);

        Account account = new Account();
        account.setId(agentId + "_lmyushi_wallet");
        account.setCurrency("lmyushi");
        account.setOwner(mao);

        idAccountMap.put(account.getId(), account);
        agentIdAccountIdMap.put(agentId, account.getId());
        return account.getId();
    }

    /**
     * 增加联盟成员玉石
     *
     * @param agentId           成员id
     * @param amount            数量
     * @param accountingSummary 摘要
     * @param giveTime          时间
     * @return
     * @throws MemberNotFoundException
     */
    public AccountingRecord giveYushiToAgent(String agentId, int amount,
                                             AccountingSummary accountingSummary, long giveTime) throws MemberNotFoundException {
        if (!agentIdAccountIdMap.containsKey(agentId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(agentId + "_lmyushi_wallet");
        AccountingRecord record = account.deposit(amount, accountingSummary, giveTime);
        return record;
    }

    /**
     * 消耗联盟成员玉石
     *
     * @param agentId           成员id
     * @param amount            数量
     * @param accountingSummary 摘要
     * @param withdrawTime      时间
     * @return
     * @throws MemberNotFoundException
     * @throws InsufficientBalanceException
     */
    public AccountingRecord withdraw(String agentId, int amount, AccountingSummary accountingSummary,
                                     long withdrawTime) throws MemberNotFoundException, InsufficientBalanceException {
        if (!agentIdAccountIdMap.containsKey(agentId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(agentId + "_lmyushi_wallet");
        AccountingRecord record = account.withdraw(amount, accountingSummary, withdrawTime);
        return record;
    }

    /**
     * 用户是否存在玉石账户
     *
     * @param agentId 用户ID
     */
    public boolean createAccountForAgentIsExist(String agentId) {
        return agentIdAccountIdMap.containsKey(agentId);

    }

}
