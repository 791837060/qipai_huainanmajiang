package com.anbang.qipai.dalianmeng.cqrs.c.domain.baoxiangui;


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
 * 成员保险柜账户管理
 *
 * @author lsc
 */
public class MemberBoxAccountManager {

    private Map<String, Account> idAccountMap = new HashMap<>();

    private Map<String, Set<String>> memberIdAccountIdsMap = new HashMap<>();

    /**
     * 创建联盟成员保险柜账户
     *
     * @param memberId   成员id
     * @param lianmengId 联盟id
     * @return
     * @throws MemberHasBoxAccountAlreadyException
     */
    public String createBoxAccountForNewMember(String memberId, String lianmengId) throws MemberHasBoxAccountAlreadyException {
        if (idAccountMap.containsKey(memberId + "_" + lianmengId + "_box_wallet")) {
            throw new MemberHasBoxAccountAlreadyException();
        }
        MemberBoxAccountOwner mao = new MemberBoxAccountOwner();
        mao.setMemberId(memberId);
        mao.setLianmengId(lianmengId);

        Account account = new Account();
        account.setId(memberId + "_" + lianmengId + "_box_wallet");
        account.setCurrency("box");
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
     * 储存联盟成员能量
     *
     * @param memberId          成员id
     * @param lianmengId        联盟id
     * @param amount            数量
     * @param accountingSummary 摘要
     * @param giveTime          时间
     * @return
     * @throws MemberNotFoundException
     */
    public AccountingRecord givePowerToMemberByLianmeng(String memberId, String lianmengId, double amount, AccountingSummary accountingSummary, long giveTime) throws MemberNotFoundException {
        if (!memberIdAccountIdsMap.containsKey(memberId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(memberId + "_" + lianmengId + "_box_wallet");
        AccountingRecord record = account.deposit(amount, accountingSummary, giveTime);
        return record;
    }

    /**
     * 提取联盟成员能量
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
    public AccountingRecord withdraw(String memberId, String lianmengId, double amount, AccountingSummary accountingSummary, long withdrawTime) throws MemberNotFoundException, InsufficientBalanceException {
        if (!memberIdAccountIdsMap.containsKey(memberId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(memberId + "_" + lianmengId + "_box_wallet");
        AccountingRecord record = account.withdraw(amount, accountingSummary, withdrawTime);
        return record;
    }

}
