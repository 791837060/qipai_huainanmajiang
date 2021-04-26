package com.anbang.qipai.admin.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface WhiteListSink {

    String WHITELIST = "whitelist";

    @Input
    SubscribableChannel whitelist();
}
