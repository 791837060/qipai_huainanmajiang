package com.anbang.qipai.qinyouquan.msg.service;

import com.anbang.qipai.qinyouquan.msg.msjobs.CommonMO;
import com.anbang.qipai.qinyouquan.msg.source.BijiGameRoomSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(BijiGameRoomSource.class)
public class BijiGameRoomMsgService {

    @Autowired
    private BijiGameRoomSource bijiGameRoomSource;

    public void removeGameRoom(List<String> gameIds) {
        CommonMO mo = new CommonMO();
        mo.setMsg("gameIds");
        mo.setData(gameIds);
        bijiGameRoomSource.bijiGameRoom().send(MessageBuilder.withPayload(mo).build());
    }

}
