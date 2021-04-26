package com.anbang.qipai.biji.msg.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GameRoomSink {

	String BIJIGAMEROOM = "bijiGameRoom";

	@Input
	SubscribableChannel bijiGameRoom();
}
