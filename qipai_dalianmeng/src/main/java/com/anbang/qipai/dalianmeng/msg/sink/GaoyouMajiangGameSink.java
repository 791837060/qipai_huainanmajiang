package com.anbang.qipai.dalianmeng.msg.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GaoyouMajiangGameSink {
	String GAOYOUMAJIANGGAME = "gaoyouMajiangGame";

	@Input
	SubscribableChannel gaoyouMajiangGame();
}
