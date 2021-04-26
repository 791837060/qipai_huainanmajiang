package com.anbang.qipai.game.msg.service;

import com.anbang.qipai.game.msg.channel.source.TaixingMajiangGameRoomSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(TaixingMajiangGameRoomSource.class)
public class TaixingMajiangGameRoomMsgService {

    @Autowired
    private TaixingMajiangGameRoomSource taixingMajiangGameRoomSource;

    public void removeGameRoom(List<String> gameIds) {
        CommonMO mo = new CommonMO();
        mo.setMsg("gameIds");
        mo.setData(gameIds);
        taixingMajiangGameRoomSource.taixingMajiangGameRoom().send(MessageBuilder.withPayload(mo).build());
    }
}
