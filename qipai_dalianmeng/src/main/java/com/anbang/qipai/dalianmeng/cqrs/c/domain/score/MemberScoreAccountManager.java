package com.anbang.qipai.dalianmeng.cqrs.c.domain.score;


import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.dml.accounting.Account;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.AccountingSummary;
import com.dml.accounting.InsufficientBalanceException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 成员积分账户管理
 *
 * @author lsc
 */
public class MemberScoreAccountManager {

    private Map<String, Account> idAccountMap = new HashMap<>();

    private Map<String, Set<String>> memberIdAccountIdsMap = new HashMap<>();

    /**
     * 创建联盟成员积分账户
     *
     * @param memberId   成员id
     * @param lianmengId 联盟id
     * @return
     * @throws MemberHasScoreAccountAlreadyException
     */
    public String createScoreAccountForNewMember(String memberId, String lianmengId)
            throws MemberHasScoreAccountAlreadyException {
        if (idAccountMap.containsKey(memberId + "_" + lianmengId + "_score_wallet")) {
            throw new MemberHasScoreAccountAlreadyException();
        }
        MemberScoreAccountOwner mao = new MemberScoreAccountOwner();
        mao.setMemberId(memberId);
        mao.setLianmengId(lianmengId);

        Account account = new Account();
        account.setId(memberId + "_" + lianmengId + "_score_wallet");
        account.setCurrency("score");
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
     * 增加联盟成员积分
     *
     * @param memberId          成员id
     * @param lianmengId        联盟id
     * @param amount            数量
     * @param accountingSummary 摘要
     * @param giveTime          时间
     * @return
     * @throws MemberNotFoundException
     */
    public AccountingRecord giveScoreToMemberByLianmeng(String memberId, String lianmengId, double amount,
                                                        AccountingSummary accountingSummary, long giveTime) throws MemberNotFoundException {
        if (!memberIdAccountIdsMap.containsKey(memberId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(memberId + "_" + lianmengId + "_score_wallet");
        AccountingRecord record = account.deposit(amount, accountingSummary, giveTime);
        return record;
    }

    /**
     * 消耗联盟成员积分
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
    public AccountingRecord withdraw(String memberId, String lianmengId, double amount, AccountingSummary accountingSummary,
                                     long withdrawTime) throws MemberNotFoundException, InsufficientBalanceException {
        if (!memberIdAccountIdsMap.containsKey(memberId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(memberId + "_" + lianmengId + "_score_wallet");
        AccountingRecord record = account.withdraw(amount, accountingSummary, withdrawTime);
        return record;
    }

}
