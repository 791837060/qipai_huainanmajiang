package com.anbang.qipai.qinyouquan.cqrs.c.domain.lianmengdiamond;


import com.anbang.qipai.qinyouquan.cqrs.c.domain.MemberNotFoundException;
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
public class LianmengDiamondAccountManager {

    private Map<String, Account> idAccountMap = new HashMap<>();

    private Map<String, String> mengzhuIdAccountIdMap = new HashMap<>();

    /**
     * 创建联盟玉石账户
     *
     * @param mengzhuId 成员id
     * @return
     * @throws MengzhuHasLianmengDiamondAccountAlreadyException
     */
    public String createDiamondAccountForMengzhu(String mengzhuId)
            throws MengzhuHasLianmengDiamondAccountAlreadyException {
        if (idAccountMap.containsKey(mengzhuId + "_lmdiamond_wallet")) {
            throw new MengzhuHasLianmengDiamondAccountAlreadyException();
        }
        LianmengDiamondAccountOwner mao = new LianmengDiamondAccountOwner();
        mao.setMengzhuId(mengzhuId);

        Account account = new Account();
        account.setId(mengzhuId + "_lmdiamond_wallet");
        account.setCurrency("lmdiamond");
        account.setOwner(mao);

        idAccountMap.put(account.getId(), account);
        mengzhuIdAccountIdMap.put(mengzhuId, account.getId());
        return account.getId();
    }

    /**
     * 增加联盟成员玉石
     *
     * @param mengzhuId           成员id
     * @param amount            数量
     * @param accountingSummary 摘要
     * @param giveTime          时间
     * @return
     * @throws MemberNotFoundException
     */
    public AccountingRecord giveDiamondToMengzhu(String mengzhuId, int amount,
                                             AccountingSummary accountingSummary, long giveTime) throws MemberNotFoundException {
        if (!mengzhuIdAccountIdMap.containsKey(mengzhuId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(mengzhuId + "_lmdiamond_wallet");
        AccountingRecord record = account.deposit(amount, accountingSummary, giveTime);
        return record;
    }

    /**
     * 消耗联盟成员玉石
     *
     * @param mengzhuId           成员id
     * @param amount            数量
     * @param accountingSummary 摘要
     * @param withdrawTime      时间
     * @return
     * @throws MemberNotFoundException
     * @throws InsufficientBalanceException
     */
    public AccountingRecord withdraw(String mengzhuId, int amount, AccountingSummary accountingSummary,
                                     long withdrawTime) throws MemberNotFoundException, InsufficientBalanceException {
        if (!mengzhuIdAccountIdMap.containsKey(mengzhuId)) {
            throw new MemberNotFoundException();
        }
        Account account = idAccountMap.get(mengzhuId + "_lmdiamond_wallet");
        AccountingRecord record = account.withdraw(amount, accountingSummary, withdrawTime);
        return record;
    }

    /**
     * 用户是否存在玉石账户
     *
     * @param mengzhuId 用户ID
     */
    public boolean createAccountForMengzhuIsExist(String mengzhuId) {
        return mengzhuIdAccountIdMap.containsKey(mengzhuId);

    }

}
