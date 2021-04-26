package com.anbang.qipai.dalianmeng.cqrs.c.service.impl;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.CreateLianmengResult;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.JoinLianmengResult;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.baoxiangui.MemberBoxAccountManager;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.lianmeng.LianmengIdManager;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.power.MemberPowerAccountManager;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.score.MemberScoreAccountManager;
import com.anbang.qipai.dalianmeng.cqrs.c.service.LianmengCmdService;
import org.springframework.stereotype.Component;

@Component
public class LianmengCmdServiceImpl extends CmdServiceBase implements LianmengCmdService {
    @Override
    public CreateLianmengResult createNewLianmengId(String memberId, Long currentTime) throws Exception {
        CreateLianmengResult result = new CreateLianmengResult();

        LianmengIdManager lianmengIdManager = singletonEntityRepository.getEntity(LianmengIdManager.class);
        String lianmengId = lianmengIdManager.createLianmengId(currentTime);
        result.setLianmengId(lianmengId);

        MemberPowerAccountManager memberPowerAccountManager = singletonEntityRepository.getEntity(MemberPowerAccountManager.class);
        String powerAccountId = memberPowerAccountManager.createPowerAccountForNewMember(memberId, lianmengId);
        result.setPowerAccountId(powerAccountId);

//        MemberBoxAccountManager memberBoxAccountManager = singletonEntityRepository.getEntity(MemberBoxAccountManager.class);
//        String boxAccountId = memberBoxAccountManager.createBoxAccountForNewMember(memberId, lianmengId);
//        result.setBoxAccountId(boxAccountId);

        MemberScoreAccountManager memberScoreAccountManager = singletonEntityRepository.getEntity(MemberScoreAccountManager.class);
        String scoreAccountId = memberScoreAccountManager.createScoreAccountForNewMember(memberId, lianmengId);
        result.setScoreAccountId(scoreAccountId);
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
        MemberPowerAccountManager memberPowerAccountManager = singletonEntityRepository.getEntity(MemberPowerAccountManager.class);
        String powerAccountId = memberPowerAccountManager.createPowerAccountForNewMember(memberId, lianmengId);
//        MemberBoxAccountManager memberBoxAccountManager = singletonEntityRepository.getEntity(MemberBoxAccountManager.class);
//        String boxAccountId = memberBoxAccountManager.createBoxAccountForNewMember(memberId, lianmengId);
        result.setPowerAccountId(powerAccountId);
//        result.setBoxAccountId(boxAccountId);
        return result;
    }

    @Override
    public String undateIdentity(String memberId, String lianmengId) throws Exception {
        MemberScoreAccountManager memberScoreAccountManager = singletonEntityRepository.getEntity(MemberScoreAccountManager.class);
        return memberScoreAccountManager.createScoreAccountForNewMember(memberId, lianmengId);
    }
}
