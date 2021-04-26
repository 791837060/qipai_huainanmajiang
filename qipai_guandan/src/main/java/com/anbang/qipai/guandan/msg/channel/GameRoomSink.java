package com.anbang.qipai.guandan.msg.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GameRoomSink {

	String GUANDANGAMEROOM = "guandanGameRoom";

	@Input
	SubscribableChannel guandanGameRoom();
}
