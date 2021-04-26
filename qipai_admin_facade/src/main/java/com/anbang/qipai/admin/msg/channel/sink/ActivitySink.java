package com.anbang.qipai.admin.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ActivitySink {

	String ACTIVITY = "activity";

	@Input
	SubscribableChannel activity();
}
