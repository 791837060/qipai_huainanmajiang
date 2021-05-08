package com.anbang.qipai.shouxianmajiang.msg.service;

import java.util.HashMap;
import java.util.Map;

import com.anbang.qipai.shouxianmajiang.msg.channel.WisecrackSource;
import com.anbang.qipai.shouxianmajiang.msg.msjobj.CommonMO;
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
