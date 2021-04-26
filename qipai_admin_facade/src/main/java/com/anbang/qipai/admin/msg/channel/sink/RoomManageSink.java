package com.anbang.qipai.admin.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RoomManageSink {

    String CHANNEL = "roomManage";

    String CREATROOM = "creatRoom";
    String UPDATEPLAYER = "updatePlayer";

    @Input
    SubscribableChannel roomManage();

}
