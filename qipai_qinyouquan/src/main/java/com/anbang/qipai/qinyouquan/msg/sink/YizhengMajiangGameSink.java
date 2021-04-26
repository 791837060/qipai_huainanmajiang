package com.anbang.qipai.qinyouquan.msg.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface YizhengMajiangGameSink {
	String YIZHENGMAJIANGGAME = "yizhengMajiangGame";

	@Input
	SubscribableChannel yizhengMajiangGame();
}
