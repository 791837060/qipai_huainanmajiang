package com.anbang.qipai.maanshanmajiang.msg.service;

import com.anbang.qipai.maanshanmajiang.msg.channel.MaanshanMajiangResultSource;
import com.anbang.qipai.maanshanmajiang.msg.msjobj.CommonMO;
import com.anbang.qipai.maanshanmajiang.msg.msjobj.MajiangHistoricalJuResult;
import com.anbang.qipai.maanshanmajiang.msg.msjobj.MajiangHistoricalPanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(MaanshanMajiangResultSource.class)
public class MaanshanMajiangResultMsgService {

	@Autowired
	private MaanshanMajiangResultSource maanshanMajiangResultSource;

	public void recordJuResult(MajiangHistoricalJuResult juResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("maanshanMajiang ju result");
		mo.setData(juResult);
		maanshanMajiangResultSource.maanshanMajiangResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void recordPanResult(MajiangHistoricalPanResult panResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("maanshanMajiang pan result");
		mo.setData(panResult);
		maanshanMajiangResultSource.maanshanMajiangResult().send(MessageBuilder.withPayload(mo).build());
	}
}
