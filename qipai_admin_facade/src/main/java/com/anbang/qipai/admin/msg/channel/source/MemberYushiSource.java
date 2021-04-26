package com.anbang.qipai.admin.msg.channel.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MemberYushiSource {
    String channel = "memberYushi";
    String addYushi = "addYushi";
    String deleteYushi = "deleteYushi";
    String updateYushi = "updateYushi";

    @Output
    MessageChannel memberYushi();
}
