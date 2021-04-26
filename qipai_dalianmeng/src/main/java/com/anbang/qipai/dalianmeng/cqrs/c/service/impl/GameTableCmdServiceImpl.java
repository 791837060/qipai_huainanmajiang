package com.anbang.qipai.dalianmeng.cqrs.c.service.impl;


import com.anbang.qipai.dalianmeng.cqrs.c.domain.game.GameTableNoManager;
import com.anbang.qipai.dalianmeng.cqrs.c.service.GameTableCmdService;
import org.springframework.stereotype.Component;

@Component
public class GameTableCmdServiceImpl extends CmdServiceBase implements GameTableCmdService {

    @Override
    public String createTable(Long createTime) {
        GameTableNoManager gameTableNoManager = singletonEntityRepository.getEntity(GameTableNoManager.class);
        String roomNo = gameTableNoManager.newNo(createTime);
        return roomNo;
    }

    @Override
    public String removeTable(String no) {
        GameTableNoManager gameTableNoManager = singletonEntityRepository.getEntity(GameTableNoManager.class);
        return gameTableNoManager.removeNo(no);
    }

}
