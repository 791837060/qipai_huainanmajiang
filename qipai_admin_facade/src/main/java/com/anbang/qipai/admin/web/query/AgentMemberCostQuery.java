package com.anbang.qipai.admin.web.query;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 代理下辖玩家日消耗query     AgentMemberDayCost.class
 *
 * @author ethan
 */
public class AgentMemberCostQuery {
    private String agentId;     // 代理id
    private String nickName;    // 代理昵称
    private Long startTime;
    private Long endTime;
    private String dayTotalCostSort;

    public Sort getSort() {
        List<Sort.Order> orderList = new ArrayList<>();
        if ("ASC".equals(dayTotalCostSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "dayTotalCost"));
        } else if ("DESC".equals(dayTotalCostSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "dayTotalCost"));
        }
        if (!orderList.isEmpty()) {
            Sort sort = new Sort(orderList);
            return sort;
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
        return sort;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getDayTotalCostSort() {
        return dayTotalCostSort;
    }

    public void setDayTotalCostSort(String dayTotalCostSort) {
        this.dayTotalCostSort = dayTotalCostSort;
    }
}
