package com.anbang.qipai.admin.msg.channel.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 发布奖品
 */
public interface SignInPrizeSource {

    String channel = "signInPrize";
    String releaseSignInPrize = "releaseSignInPrize";
    String sendOutGoods = "sendOutGoods";

    @Output
    MessageChannel signInPrize();
}
