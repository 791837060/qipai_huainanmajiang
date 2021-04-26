package com.anbang.qipai.dalianmeng.msg.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface BijiResultSink {
    String BIJIRESULT = "bijiResult";

    @Input
    SubscribableChannel bijiResult();
}
