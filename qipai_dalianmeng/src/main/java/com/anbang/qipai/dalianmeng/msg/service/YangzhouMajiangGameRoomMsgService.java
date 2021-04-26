package com.anbang.qipai.dalianmeng.msg.service;


import com.anbang.qipai.dalianmeng.msg.msjobs.CommonMO;
import com.anbang.qipai.dalianmeng.msg.source.YangzhouMajiangGameRoomSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(YangzhouMajiangGameRoomSource.class)
public class YangzhouMajiangGameRoomMsgService {

	@Autowired
	private YangzhouMajiangGameRoomSource yangzhouMajiangGameRoomSource;

	public void removeGameRoom(List<String> gameIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg("gameIds");
		mo.setData(gameIds);
        yangzhouMajiangGameRoomSource.yangzhouMajiangGameRoom().send(MessageBuilder.withPayload(mo).build());
	}
}
