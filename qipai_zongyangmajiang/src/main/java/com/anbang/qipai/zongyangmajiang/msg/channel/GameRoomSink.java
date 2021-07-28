package com.anbang.qipai.zongyangmajiang.msg.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GameRoomSink {

	String HONGZHONGMAJIANGGAMEROOM = "hongzhongMajiangGameRoom";

	@Input
	SubscribableChannel hongzhongMajiangGameRoom();
}
