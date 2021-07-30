package com.anbang.qipai.game.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ZongyangMajiangResultSink {
    String ZONGYANGMAJIANGRESULT = "zongyangMajiangResult";

    @Input
    SubscribableChannel zongyangMajiangResult();
}
