package com.anbang.qipai.doudizhu.msg.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DoudizhuGameSource {
	@Output
	MessageChannel doudizhuGame();
}
