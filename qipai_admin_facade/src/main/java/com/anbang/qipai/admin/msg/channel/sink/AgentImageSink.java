package com.anbang.qipai.admin.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface AgentImageSink {
	String AGENTIMAGE = "agentImage";

	@Input
	SubscribableChannel agentImage();
}
