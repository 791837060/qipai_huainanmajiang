package com.anbang.qipai.shouxianmajiang.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.shouxianmajiang.msg.channel.ShouxianMajiangResultSource;
import com.anbang.qipai.shouxianmajiang.msg.msjobj.CommonMO;
import com.anbang.qipai.shouxianmajiang.msg.msjobj.MajiangHistoricalJuResult;
import com.anbang.qipai.shouxianmajiang.msg.msjobj.MajiangHistoricalPanResult;

@EnableBinding(ShouxianMajiangResultSource.class)
public class ShouxianMajiangResultMsgService {

	@Autowired
	private ShouxianMajiangResultSource shouxianmajiangResultSource;

	public void recordJuResult(MajiangHistoricalJuResult juResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("shouxianMajiang ju result");
		mo.setData(juResult);
		shouxianmajiangResultSource.shouxianMajiangResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void recordPanResult(MajiangHistoricalPanResult panResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("shouxianMajiang pan result");
		mo.setData(panResult);
		shouxianmajiangResultSource.shouxianMajiangResult().send(MessageBuilder.withPayload(mo).build());
	}
}
