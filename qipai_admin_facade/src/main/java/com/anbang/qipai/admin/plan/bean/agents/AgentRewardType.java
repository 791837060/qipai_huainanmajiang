package com.anbang.qipai.admin.plan.bean.agents;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 返利类型
 */
public class  AgentRewardType {

    public static Map rewardTypeMap;

    static {
        rewardTypeMap = new LinkedHashMap();
        rewardTypeMap.put("一级","member reward");
        rewardTypeMap.put("二级","junior reward");
    }

    public static String getRewardType(String value) {
        switch (value) {
            case "member reward" :
                return "一级";
            case "junior reward":
                return "二级";
        }
        return "";
    }
}
