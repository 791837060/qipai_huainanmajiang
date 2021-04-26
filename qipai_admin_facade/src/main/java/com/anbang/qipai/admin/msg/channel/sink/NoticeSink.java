package com.anbang.qipai.admin.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 接收系统公告返回消息接口
 * 
 * @author 程佳 2018.6.1
 **/
public interface NoticeSink {

	String notice = "notice";

	@Input
	SubscribableChannel notice();
}
