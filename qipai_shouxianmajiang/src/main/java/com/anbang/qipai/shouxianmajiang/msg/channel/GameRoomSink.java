package com.anbang.qipai.shouxianmajiang.msg.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GameRoomSink {

	String SHOUXIANGAMEROOM = "shouxianGameRoom";

	@Input
	SubscribableChannel shouxianGameRoom();
}
