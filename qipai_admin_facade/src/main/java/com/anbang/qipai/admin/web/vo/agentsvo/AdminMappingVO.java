package com.anbang.qipai.admin.web.vo.agentsvo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 类型映射VO
 */
public class AdminMappingVO {
	public static Map agentPayType; // 代理支付类型

	static {
		agentPayType = new LinkedHashMap();
		agentPayType.put("alipay", "支付宝");
		agentPayType.put("wxpay", "微信");
	}
}
