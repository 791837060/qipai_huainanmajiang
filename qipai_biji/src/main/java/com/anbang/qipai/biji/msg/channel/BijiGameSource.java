package com.anbang.qipai.biji.msg.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface BijiGameSource {

	@Output
	MessageChannel bijiGame();
}
