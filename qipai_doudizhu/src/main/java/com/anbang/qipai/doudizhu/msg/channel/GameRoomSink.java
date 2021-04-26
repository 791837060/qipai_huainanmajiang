package com.anbang.qipai.doudizhu.msg.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GameRoomSink {

	String DOUDIZHUGAMEROOM = "doudizhuGameRoom";

	@Input
	SubscribableChannel doudizhuGameRoom();
}
