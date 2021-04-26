package com.anbang.qipai.qinyouquan.cqrs.c.domain.memberdiamond;


import com.anbang.qipai.qinyouquan.cqrs.c.domain.MemberNotFoundException;
import com.dml.accounting.Account;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.AccountingSummary;
import com.dml.accounting.InsufficientBalanceException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 成员钻石账户管理
 *
 * @author lsc
 */
public class MemberDiamondAccountManager {

    private Map<String, Account> idAccountMap = new HashMap<>();

    private Map<String, Set<String>> memberIdAccountIdsMap = new HashMap<>();

    /**
     * 创建联盟成员钻石账户
     *
     * @param memberId   成员id
     * @param lianmengId 联盟id
     * @return
     * @throws MemberHasDiamondAccountAlreadyException
     */
    public String createDiamondAccountForNewMember(String memberId, String lianmengId)
            throws MemberHasDiamondAccountAlreadyException {
        if (idAccountMap.containsKey(memberId + "_" + lianmengId + "_diamond_wallet")) {
            throw new MemberHasDiamondAccountAlreadyException();
        }
        MemberDiamondAccountOwner mao = new MemberDiamondAccountOwner();
        mao.setMemberId(memberId);
        mao.setLianmengId(lianmengId);

        Account account = new Account();
        account.setId(memberId + "_" + lianmengId + "_diamond_wallet");
        account.setCurrency("memberdiamond");
        account.setOwner(mao);

        idAccountMap.put(account.getId(), account);
        Set<String> accountIds = memberIdAccountIdsMap.get(memberId);
        if (accountIds == null) {
            accountIds = new HashSet<>();
        }
        accountIds.add(account.getId());
        memberIdAccountIdsMap.put(memberId, accountIds);
        return account.getId();
    }

    /**
     * 增加联盟成员钻石
     *
     * @param memberId          成员id
     * @param lianmengId        联盟id
     * @param amount            数量
     * @param accountingSummary 摘要
     * @param giveTime          时间
     * @return
     * @throws MemberNotFoundException
     */
    public AccountingRecord givePowerToMemberByLianmeng(String memberId, String lianmengId, int amount,
                                                        AccountingSummary accountingSummary, long giveTime) throws MemberNotFoundException {
        if (!memberIdAccountIdsMap.containsKey(memberId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(memberId + "_" + lianmengId + "_diamond_wallet");
        AccountingRecord record = account.deposit(amount, accountingSummary, giveTime);
        return record;
    }

    /**
     * 消耗联盟成员钻石
     *
     * @param memberId          成员id
     * @param lianmengId        联盟id
     * @param amount            数量
     * @param accountingSummary 摘要
     * @param withdrawTime      时间
     * @return
     * @throws MemberNotFoundException
     * @throws InsufficientBalanceException
     */
    public AccountingRecord withdraw(String memberId, String lianmengId, int amount, AccountingSummary accountingSummary,
                                     long withdrawTime) throws MemberNotFoundException, InsufficientBalanceException {
        if (!memberIdAccountIdsMap.containsKey(memberId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(memberId + "_" + lianmengId + "_diamond_wallet");
        AccountingRecord record = account.withdraw(amount, accountingSummary, withdrawTime);
        return record;
    }

    /**
     * 可以透支消费钻石
     *
     * @param memberId
     * @param lianmengId
     * @param amount
     * @param accountingSummary
     * @param withdrawTime
     * @return
     * @throws MemberNotFoundException
     * @throws InsufficientBalanceException
     */
    public AccountingRecord withdrawAnyway(String memberId, String lianmengId, int amount, AccountingSummary accountingSummary,
                                           long withdrawTime) throws MemberNotFoundException, InsufficientBalanceException {
        if (!memberIdAccountIdsMap.containsKey(memberId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(memberId + "_" + lianmengId + "_diamond_wallet");
        AccountingRecord record = account.withdraw(amount, accountingSummary, withdrawTime);
        return record;
    }

}
