package com.anbang.qipai.game.msg.service;

import com.anbang.qipai.game.msg.channel.source.MaanshanMajiangGameRoomSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(MaanshanMajiangGameRoomSource.class)
public class MaanshanMajiangGameRoomMsgService {

    @Autowired
    private MaanshanMajiangGameRoomSource maanshanMajiangGameRoomSource;

    public void removeGameRoom(List<String> gameIds) {
        CommonMO mo = new CommonMO();
        mo.setMsg("gameIds");
        mo.setData(gameIds);
        maanshanMajiangGameRoomSource.maanshanMajiangGameRoom().send(MessageBuilder.withPayload(mo).build());
    }
}
