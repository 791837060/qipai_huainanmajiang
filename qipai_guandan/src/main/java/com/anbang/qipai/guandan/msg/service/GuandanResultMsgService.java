package com.anbang.qipai.guandan.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.guandan.msg.channel.GuandanResultSource;
import com.anbang.qipai.guandan.msg.msjobj.CommonMO;
import com.anbang.qipai.guandan.msg.msjobj.PukeHistoricalJuResult;
import com.anbang.qipai.guandan.msg.msjobj.PukeHistoricalPanResult;

@EnableBinding(GuandanResultSource.class)
public class GuandanResultMsgService {

	@Autowired
	private GuandanResultSource guandanResultSource;

	public void recordJuResult(PukeHistoricalJuResult juResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("guandan ju result");
		mo.setData(juResult);
		guandanResultSource.guandanResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void recordPanResult(PukeHistoricalPanResult panResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("guandan pan result");
		mo.setData(panResult);
		guandanResultSource.guandanResult().send(MessageBuilder.withPayload(mo).build());
	}
}
