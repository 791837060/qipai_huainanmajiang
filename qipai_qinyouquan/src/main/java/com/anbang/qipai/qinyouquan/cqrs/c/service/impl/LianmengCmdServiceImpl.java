package com.anbang.qipai.qinyouquan.cqrs.c.service.impl;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.CreateLianmengResult;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.JoinLianmengResult;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.lianmeng.LianmengIdManager;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.memberdiamond.MemberDiamondAccountManager;
import com.anbang.qipai.qinyouquan.cqrs.c.service.LianmengCmdService;
import org.springframework.stereotype.Component;

@Component
public class LianmengCmdServiceImpl extends CmdServiceBase implements LianmengCmdService {
    @Override
    public CreateLianmengResult createNewLianmengId(String memberId, Long currentTime) throws Exception {
        CreateLianmengResult result = new CreateLianmengResult();

        LianmengIdManager lianmengIdManager = singletonEntityRepository.getEntity(LianmengIdManager.class);
        String lianmengId = lianmengIdManager.createLianmengId(currentTime);
        result.setLianmengId(lianmengId);
        return result;
    }

    @Override
    public String removeLianmeng(String lianmengId) {
        LianmengIdManager lianmengIdManager = singletonEntityRepository.getEntity(LianmengIdManager.class);
        lianmengIdManager.removeLianmengId(lianmengId);
        return lianmengId;
    }

    @Override
    public JoinLianmengResult joinLianmeng(String memberId, String lianmengId) throws Exception {
        JoinLianmengResult result = new JoinLianmengResult();
        MemberDiamondAccountManager memberDiamondAccountManager = singletonEntityRepository.getEntity(MemberDiamondAccountManager.class);
        String powerAccountId = memberDiamondAccountManager.createDiamondAccountForNewMember(memberId, lianmengId);
        result.setDiamondAccountId(powerAccountId);
        return result;
    }


}
