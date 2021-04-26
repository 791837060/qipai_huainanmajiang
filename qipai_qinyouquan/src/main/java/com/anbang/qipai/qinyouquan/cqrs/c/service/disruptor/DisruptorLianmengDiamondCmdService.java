package com.anbang.qipai.qinyouquan.cqrs.c.service.disruptor;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.lianmengdiamond.MengzhuHasLianmengDiamondAccountAlreadyException;
import com.anbang.qipai.qinyouquan.cqrs.c.service.LianmengDiamondCmdService;
import com.anbang.qipai.qinyouquan.cqrs.c.service.impl.LianmengDiamondCmdServiceImpl;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "lianmengDiamondCmdService")
public class DisruptorLianmengDiamondCmdService extends DisruptorCmdServiceBase implements LianmengDiamondCmdService {
    @Autowired
    private LianmengDiamondCmdServiceImpl lianmengDiamondCmdService;

    @Override
    public String createAccountForMengzhu(String mengzhuId, Long currentTime) throws MengzhuHasLianmengDiamondAccountAlreadyException {
        CommonCommand cmd = new CommonCommand(LianmengDiamondCmdServiceImpl.class.getName(), "createAccountForMengzhu", mengzhuId,
                currentTime);
        DeferredResult<String> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            String accountId = lianmengDiamondCmdService.createAccountForMengzhu(cmd.getParameter(),
                    cmd.getParameter());
            return accountId;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            if (e instanceof MengzhuHasLianmengDiamondAccountAlreadyException) {
                throw (MengzhuHasLianmengDiamondAccountAlreadyException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public AccountingRecord giveDiamondToMengzhu(String mengzhuId, Integer amount, String textSummary, Long currentTime) throws MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(LianmengDiamondCmdServiceImpl.class.getName(), "giveDiamondToMengzhu", mengzhuId, amount,
                textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            AccountingRecord accountingRecord = lianmengDiamondCmdService.giveDiamondToMengzhu(cmd.getParameter(),
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
    public AccountingRecord withdraw(String mengzhuId, Integer amount, String textSummary, Long currentTime) throws InsufficientBalanceException, MemberNotFoundException {
        CommonCommand cmd = new CommonCommand(LianmengDiamondCmdServiceImpl.class.getName(), "withdraw", mengzhuId, amount,
                textSummary, currentTime);
        DeferredResult<AccountingRecord> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            AccountingRecord accountingRecord = lianmengDiamondCmdService.withdraw(cmd.getParameter(),
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
        CommonCommand cmd = new CommonCommand(LianmengDiamondCmdServiceImpl.class.getName(), "createAccountForAgentIsExist", agentId);
        DeferredResult<Boolean> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            return lianmengDiamondCmdService.createAccountForAgentIsExist(cmd.getParameter());
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
