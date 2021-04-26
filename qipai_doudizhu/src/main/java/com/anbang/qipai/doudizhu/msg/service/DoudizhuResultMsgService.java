package com.anbang.qipai.doudizhu.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.doudizhu.msg.channel.DoudizhuResultSource;
import com.anbang.qipai.doudizhu.msg.msjobj.CommonMO;
import com.anbang.qipai.doudizhu.msg.msjobj.PukeHistoricalJuResult;
import com.anbang.qipai.doudizhu.msg.msjobj.PukeHistoricalPanResult;

@EnableBinding(DoudizhuResultSource.class)
public class DoudizhuResultMsgService {

	@Autowired
	private DoudizhuResultSource doudizhuResultSource;

	public void recordJuResult(PukeHistoricalJuResult juResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("doudizhu ju result");
		mo.setData(juResult);
		doudizhuResultSource.doudizhuResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void recordPanResult(PukeHistoricalPanResult panResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("doudizhu pan result");
		mo.setData(panResult);
		doudizhuResultSource.doudizhuResult().send(MessageBuilder.withPayload(mo).build());
	}
}
