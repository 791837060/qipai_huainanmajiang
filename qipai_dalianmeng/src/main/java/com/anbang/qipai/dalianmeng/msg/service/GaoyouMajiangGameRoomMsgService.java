package com.anbang.qipai.dalianmeng.msg.service;

import com.anbang.qipai.dalianmeng.msg.msjobs.CommonMO;
import com.anbang.qipai.dalianmeng.msg.source.GaoyouMajiangGameRoomSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(GaoyouMajiangGameRoomSource.class)
public class GaoyouMajiangGameRoomMsgService {

	@Autowired
	private GaoyouMajiangGameRoomSource gaoyouMajiangGameRoomSource;

	public void removeGameRoom(List<String> gameIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("gameIds");
		mo.setData(gameIds);
        gaoyouMajiangGameRoomSource.gaoyouMajiangGameRoom().send(MessageBuilder.withPayload(mo).build());
	}
}
