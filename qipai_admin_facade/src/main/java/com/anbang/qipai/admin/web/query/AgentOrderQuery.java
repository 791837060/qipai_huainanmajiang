package com.anbang.qipai.admin.web.query;

import com.anbang.qipai.admin.plan.bean.agents.AgentOrder;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 代理充值query
 */
public class AgentOrderQuery extends AgentOrder {
    private Long startTime;
    private Long endTime;
    private String deliveTimeSort;

    public Sort getSort(){
        List<Sort.Order> orderList = new ArrayList<>();
        if ("ASC".equals(deliveTimeSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "deliveTime"));
        } else if ("DESC".equals(deliveTimeSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "deliveTime"));
        }
        if (!orderList.isEmpty()) {
            Sort sort = new Sort(orderList);
            return sort;
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "deliveTime"));
        return sort;
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

    public String getDeliveTimeSort() {
        return deliveTimeSort;
    }

    public void setDeliveTimeSort(String deliveTimeSort) {
        this.deliveTimeSort = deliveTimeSort;
    }
}
