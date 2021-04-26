package com.anbang.qipai.maanshanmajiang.msg.service;

import java.util.HashMap;
import java.util.Map;

import com.anbang.qipai.maanshanmajiang.msg.msjobj.CommonMO;
import com.anbang.qipai.maanshanmajiang.msg.channel.WisecrackSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(WisecrackSource.class)
public class WiseCrackMsgServcie {
	@Autowired
	private WisecrackSource wisecrackSource;

	public void wisecrack(String memberId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("wisecrack");
		Map data = new HashMap();
		data.put("memberId", memberId);
		mo.setData(data);
		wisecrackSource.wisecrack().send(MessageBuilder.withPayload(mo).build());
	}
}
