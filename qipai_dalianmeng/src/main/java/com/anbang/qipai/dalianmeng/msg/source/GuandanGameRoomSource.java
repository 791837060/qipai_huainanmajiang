package com.anbang.qipai.dalianmeng.msg.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface GuandanGameRoomSource {
	@Output
	MessageChannel guandanGameRoom();
}
