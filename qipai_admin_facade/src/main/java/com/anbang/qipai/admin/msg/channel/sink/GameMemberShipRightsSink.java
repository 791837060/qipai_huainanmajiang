package com.anbang.qipai.admin.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 接收游戏大厅权益返回消息
 * 
 * @author 程佳 2018.6.13
 **/
public interface GameMemberShipRightsSink {

	String memberrightsconfiguration = "memberrightsconfiguration";

	@Input
	SubscribableChannel memberrightsconfiguration();
}
