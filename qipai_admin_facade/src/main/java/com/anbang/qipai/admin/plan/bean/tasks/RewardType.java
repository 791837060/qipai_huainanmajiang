package com.anbang.qipai.admin.plan.bean.tasks;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 任务奖励类型
 * 
 * @author lsc
 *
 */
public enum RewardType {
	YUSHI, LIQUAN, VIPTIME, HONGBAODIAN, HONGBAORMB;

	public static Map<String,String> toMap() {
		Map map = new LinkedHashMap();
		map.put("YUSHI","玉石");
		map.put("LIQUAN","礼券");
		map.put("VIPTIME","VIP时间");
		map.put("HONGBAODIAN","红包点");
		map.put("HONGBAORMB","现金");
		return map;
	}
}
