package com.anbang.qipai.biji.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.biji.msg.channel.BijiResultSource;
import com.anbang.qipai.biji.msg.msjobj.CommonMO;
import com.anbang.qipai.biji.msg.msjobj.PukeHistoricalJuResult;
import com.anbang.qipai.biji.msg.msjobj.PukeHistoricalPanResult;

@EnableBinding(BijiResultSource.class)
public class BijiResultMsgService {

	@Autowired
	private BijiResultSource bijiResultSource;

	public void recordJuResult(PukeHistoricalJuResult juResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("biji ju result");
		mo.setData(juResult);
		bijiResultSource.bijiResult().send(MessageBuilder.withPayload(mo).build());
	}

	public void recordPanResult(PukeHistoricalPanResult panResult) {
		CommonMO mo = new CommonMO();
		mo.setMsg("biji pan result");
		mo.setData(panResult);
		bijiResultSource.bijiResult().send(MessageBuilder.withPayload(mo).build());
	}
}
