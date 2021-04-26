package com.anbang.qipai.admin.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MemberXiuxianchangGoldsSink {

	String MEMBERXIUXIANCHANGGOLDS = "memberXiuxianchangGolds";

	@Input
	SubscribableChannel memberXiuxianchangGolds();
}
