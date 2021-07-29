package com.anbang.qipai.game.msg.service;

import com.anbang.qipai.game.msg.channel.source.HongzhongMajiangGameRoomSource;
import com.anbang.qipai.game.msg.channel.source.HuainanMajiangGameRoomSource;
import com.anbang.qipai.game.msg.msjobj.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(HuainanMajiangGameRoomSource.class)
public class HuainanMajiangGameRoomMsgService {

    @Autowired
    private HuainanMajiangGameRoomSource huainanMajiangGameRoomSource;

    public void removeGameRoom(List<String> gameIds) {
        CommonMO mo = new CommonMO();
        mo.setMsg("gameIds");
        mo.setData(gameIds);
        huainanMajiangGameRoomSource.huainanMajiangGameRoom().send(MessageBuilder.withPayload(mo).build());
    }
}
