package com.anbang.qipai.game.msg.service;

import com.anbang.qipai.game.msg.channel.source.HuangshibaGameRoomSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(HuangshibaGameRoomSource.class)
public class HuangshibaGameRoomMsgService {
    @Autowired
    private HuangshibaGameRoomSource huangshibaGameRoomSource;

    public void removeGameRoom(List<String> gameIds) {
        CommonMO mo = new CommonMO();
        mo.setMsg("gameIds");
        mo.setData(gameIds);
        huangshibaGameRoomSource.huangshibaGameRoom().send(MessageBuilder.withPayload(mo).build());
    }
}
