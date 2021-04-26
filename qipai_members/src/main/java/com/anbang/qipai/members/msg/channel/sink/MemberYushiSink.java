package com.anbang.qipai.members.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MemberYushiSink {
    String channel = "memberYushi";
    String addYushi = "addYushi";
    String deleteYushi = "deleteYushi";
    String updateYushi = "updateYushi";

    @Input
    SubscribableChannel memberYushi();
}
