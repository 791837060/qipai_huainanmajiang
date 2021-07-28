package com.anbang.qipai.zongyangmajiang.msg.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface HongzhongMajiangResultSource {
	@Output
	MessageChannel hongzhongMajiangResult();
}
