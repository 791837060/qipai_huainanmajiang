package com.anbang.qipai.guandan.msg.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface GuandankouGameSource {
	@Output
	MessageChannel guandanGame();
}
