package com.anbang.qipai.maanshanmajiang.msg.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GameRoomSink {

	String MAANSHANMAJIANGROOM = "maanshanMajiangGameRoom";

	@Input
	SubscribableChannel maanshanMajiangGameRoom();
}
