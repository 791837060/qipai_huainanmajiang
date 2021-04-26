package com.anbang.qipai.dalianmeng.msg.service;

import com.anbang.qipai.dalianmeng.msg.msjobs.CommonMO;
import com.anbang.qipai.dalianmeng.msg.source.GuandanGameRoomSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(GuandanGameRoomSource.class)
public class GuandanGameRoomMsgService {

	@Autowired
	private GuandanGameRoomSource guandanGameRoomSource;

	public void removeGameRoom(List<String> gameIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("gameIds");
		mo.setData(gameIds);
		guandanGameRoomSource.guandanGameRoom().send(MessageBuilder.withPayload(mo).build());
	}
}
