package com.anbang.qipai.admin.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**接收会员等级
 * @author 程佳 2018.6.22
 * **/
public interface MemberGradeSink {

	String grade="grade";
	
	@Input
	MessageChannel grade();
}
