package com.anbang.qipai.qinyouquan.cqrs.c.service.impl;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.lianmengdiamond.MengzhuHasLianmengDiamondAccountAlreadyException;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.lianmengdiamond.LianmengDiamondAccountManager;
import com.anbang.qipai.qinyouquan.cqrs.c.service.LianmengDiamondCmdService;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.dml.accounting.TextAccountingSummary;
import org.springframework.stereotype.Component;

@Component
public class LianmengDiamondCmdServiceImpl extends CmdServiceBase implements LianmengDiamondCmdService {
    @Override
    public String createAccountForMengzhu(String mengzhuId, Long currentTime) throws MengzhuHasLianmengDiamondAccountAlreadyException {
        LianmengDiamondAccountManager lianmengDiamondAccountManager = singletonEntityRepository.getEntity(LianmengDiamondAccountManager.class);
        return lianmengDiamondAccountManager.createDiamondAccountForMengzhu(mengzhuId);
    }

    @Override
    public AccountingRecord giveDiamondToMengzhu(String mengzhuId, Integer amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        LianmengDiamondAccountManager lianmengDiamondAccountManager = singletonEntityRepository.getEntity(LianmengDiamondAccountManager.class);
        return lianmengDiamondAccountManager.giveDiamondToMengzhu(mengzhuId, amount, new TextAccountingSummary(textSummary),
                currentTime);
    }

    @Override
    public AccountingRecord withdraw(String mengzhuId, Integer amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        LianmengDiamondAccountManager lianmengDiamondAccountManager = singletonEntityRepository.getEntity(LianmengDiamondAccountManager.class);
        return lianmengDiamondAccountManager.withdraw(mengzhuId, amount, new TextAccountingSummary(textSummary), currentTime);
    }

    @Override
    public boolean createAccountForAgentIsExist(String mengzhuId) {
        LianmengDiamondAccountManager lianmengDiamondAccountManager = singletonEntityRepository.getEntity(LianmengDiamondAccountManager.class);
        return lianmengDiamondAccountManager.createAccountForMengzhuIsExist(mengzhuId);
    }

}
