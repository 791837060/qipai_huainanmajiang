package com.anbang.qipai.members.msg.channel.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MemberLoginLimitRecordSource {
	@Output
	MessageChannel memberLoginLimitRecord();
}
