package com.anbang.qipai.admin.web.query;

import com.anbang.qipai.admin.plan.bean.shop.HongbaodianProductRecord;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 红包点商城兑换记录
 */
public class HongbaoRecordQuery extends HongbaodianProductRecord {
    private Long startTime;
    private Long endTime;

    private String amountSort;
    private String createTimeSort;
    private String deliverTimeScot;

    public Sort getSort() {
        List<Sort.Order> orderList = new ArrayList<>();
        if ("ASC".equals(amountSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "amountSort"));
        } else if ("DESC".equals(amountSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "amountSort"));
        }
        if ("ASC".equals(deliverTimeScot)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "deliverTimeScot"));
        } else if ("DESC".equals(deliverTimeScot)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "deliverTimeScot"));
        }
        if ("ASC".equals(createTimeSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "createTime"));
        } else if ("DESC".equals(createTimeSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "createTime"));
        }
        if (!orderList.isEmpty()) {
            Sort sort = new Sort(orderList);
            return sort;
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
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

    public String getAmountSort() {
        return amountSort;
    }

    public void setAmountSort(String amountSort) {
        this.amountSort = amountSort;
    }

    public String getCreateTimeSort() {
        return createTimeSort;
    }

    public void setCreateTimeSort(String createTimeSort) {
        this.createTimeSort = createTimeSort;
    }

    public String getDeliverTimeScot() {
        return deliverTimeScot;
    }

    public void setDeliverTimeScot(String deliverTimeScot) {
        this.deliverTimeScot = deliverTimeScot;
    }
}
