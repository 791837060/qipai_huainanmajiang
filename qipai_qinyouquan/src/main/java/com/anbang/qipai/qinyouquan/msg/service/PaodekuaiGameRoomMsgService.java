package com.anbang.qipai.qinyouquan.msg.service;


import com.anbang.qipai.qinyouquan.msg.msjobs.CommonMO;
import com.anbang.qipai.qinyouquan.msg.source.PaodekuaiGameRoomSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(PaodekuaiGameRoomSource.class)
public class PaodekuaiGameRoomMsgService {

	@Autowired
	private PaodekuaiGameRoomSource paodekuaiGameRoomSource;

	public void removeGameRoom(List<String> gameIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("gameIds");
		mo.setData(gameIds);
		paodekuaiGameRoomSource.paodekuaiGameRoom().send(MessageBuilder.withPayload(mo).build());
	}
}
