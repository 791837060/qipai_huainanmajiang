package com.anbang.qipai.qinyouquan.msg.service;

import com.anbang.qipai.qinyouquan.msg.msjobs.CommonMO;
import com.anbang.qipai.qinyouquan.msg.source.HongzhongMajiangGameRoomSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(HongzhongMajiangGameRoomSource.class)
public class HongzhongMajiangGameRoomMsgService {

	@Autowired
	private HongzhongMajiangGameRoomSource hongzhongMajiangGameRoomSource;

	public void removeGameRoom(List<String> gameIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("gameIds");
		mo.setData(gameIds);
        hongzhongMajiangGameRoomSource.hongzhongMajiangGameRoom().send(MessageBuilder.withPayload(mo).build());
	}
}
