package com.anbang.qipai.dalianmeng.cqrs.c.service.disruptor;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.CreateLianmengResult;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.JoinLianmengResult;
import com.anbang.qipai.dalianmeng.cqrs.c.service.LianmengCmdService;
import com.anbang.qipai.dalianmeng.cqrs.c.service.impl.LianmengCmdServiceImpl;
import com.highto.framework.concurrent.DeferredResult;
import com.highto.framework.ddd.CommonCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "lianmengCmdService")
public class DisruptorLianmengCmdService extends DisruptorCmdServiceBase implements LianmengCmdService {
    @Autowired
    private LianmengCmdServiceImpl lianmengCmdServiceImpl;

    @Override
    public CreateLianmengResult createNewLianmengId(String memberId, Long currentTime) throws Exception {
        CommonCommand cmd = new CommonCommand(LianmengCmdServiceImpl.class.getName(), "createNewLianmengId", memberId, currentTime);
        DeferredResult<CreateLianmengResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            CreateLianmengResult createLianmengResult = lianmengCmdServiceImpl.createNewLianmengId(cmd.getParameter(), cmd.getParameter());
            return createLianmengResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String removeLianmeng(String lianmengId) throws Exception{
        CommonCommand cmd = new CommonCommand(LianmengCmdServiceImpl.class.getName(), "removeLianmeng", lianmengId);
        DeferredResult<String> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            String id = lianmengCmdServiceImpl.removeLianmeng(cmd.getParameter());
            return id;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public JoinLianmengResult joinLianmeng(String memberId, String lianmengId) throws Exception {
        CommonCommand cmd = new CommonCommand(LianmengCmdServiceImpl.class.getName(), "joinLianmeng", memberId, lianmengId);
        DeferredResult<JoinLianmengResult> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            JoinLianmengResult joinLianmengResult = lianmengCmdServiceImpl.joinLianmeng(cmd.getParameter(), cmd.getParameter());
            return joinLianmengResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String undateIdentity(String memberId, String lianmengId) throws Exception {
        CommonCommand cmd = new CommonCommand(LianmengCmdServiceImpl.class.getName(), "undateIdentity", memberId, lianmengId);
        DeferredResult<String> result = publishEvent(disruptorFactory.getCoreCmdDisruptor(), cmd, () -> {
            String undateIdentityResult = lianmengCmdServiceImpl.undateIdentity(cmd.getParameter(), cmd.getParameter());
            return undateIdentityResult;
        });
        try {
            return result.getResult();
        } catch (Exception e) {
            throw e;
        }
    }
}
