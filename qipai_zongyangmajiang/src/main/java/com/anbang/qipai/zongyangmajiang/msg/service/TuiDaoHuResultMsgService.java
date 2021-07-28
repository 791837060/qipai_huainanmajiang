package com.anbang.qipai.zongyangmajiang.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.zongyangmajiang.msg.channel.HongzhongMajiangResultSource;
import com.anbang.qipai.zongyangmajiang.msg.msjobj.CommonMO;
import com.anbang.qipai.zongyangmajiang.msg.msjobj.MajiangHistoricalJuResult;
import com.anbang.qipai.zongyangmajiang.msg.msjobj.MajiangHistoricalPanResult;

@EnableBinding(HongzhongMajiangResultSource.class)
public class TuiDaoHuResultMsgService {

	@Autowired
	private HongzhongMajiangResultSource tuidaohuResultSource;

	public void recordJuResult(MajiangHistoricalJuResult juResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("hongzhongMajiang ju result");
		mo.setData(juResult);
		tuidaohuResultSource.hongzhongMajiangResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void recordPanResult(MajiangHistoricalPanResult panResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("hongzhongMajiang pan result");
		mo.setData(panResult);
		tuidaohuResultSource.hongzhongMajiangResult().send(MessageBuilder.withPayload(mo).build());
	}
}
