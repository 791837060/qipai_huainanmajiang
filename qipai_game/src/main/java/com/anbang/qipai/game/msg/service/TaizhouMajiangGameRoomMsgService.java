package com.anbang.qipai.game.msg.service;

import com.anbang.qipai.game.msg.channel.source.TaizhouMajiangGameRoomSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(TaizhouMajiangGameRoomSource.class)
public class TaizhouMajiangGameRoomMsgService {

    @Autowired
    private TaizhouMajiangGameRoomSource taizhouMajiangGameRoomSource;

    public void removeGameRoom(List<String> gameIds) {
        CommonMO mo = new CommonMO();
        mo.setMsg("gameIds");
        mo.setData(gameIds);
        taizhouMajiangGameRoomSource.taizhouMajiangGameRoom().send(MessageBuilder.withPayload(mo).build());
    }
}
