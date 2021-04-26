package com.anbang.qipai.admin.msg.channel.sink.resultsink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GuandanGameSink {
    String GUANDANGAME = "guandanGame";

    @Input
    SubscribableChannel guandanGame();
}
