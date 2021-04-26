package com.anbang.qipai.admin.msg.channel.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface JuPrizeSource {
    @Output
    MessageChannel JuPrize();
}
