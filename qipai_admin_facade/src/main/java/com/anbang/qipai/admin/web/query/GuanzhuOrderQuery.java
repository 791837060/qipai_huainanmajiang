package com.anbang.qipai.admin.web.query;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 馆主商城查询query
 *
 * @author ethan
 */
public class GuanzhuOrderQuery {
    private String payerId;// 付款人id(用户)
    private String payerName;// 付款人昵称
    private String receiverId;// 收货人id(代理)
    private String receiverName;// 收货人名字

    private String numberSort;// 数量sort
    private String totalamountSort; //总价sort
    private String agentTotalSaleSort; //总额sort

    public Sort getSort() {
        List<Sort.Order> orderList = new ArrayList<>();
        if ("ASC".equals(numberSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "number"));
        } else if ("DESC".equals(numberSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "number"));
        }
        if ("ASC".equals(totalamountSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "totalamount"));
        } else if ("DESC".equals(totalamountSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "totalamount"));
        }
        if ("ASC".equals(agentTotalSaleSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "agentTotalSale"));
        } else if ("DESC".equals(agentTotalSaleSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "agentTotalSale"));
        }
        if (!orderList.isEmpty()) {
            Sort sort = new Sort(orderList);
            return sort;
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
        return sort;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getNumberSort() {
        return numberSort;
    }

    public void setNumberSort(String numberSort) {
        this.numberSort = numberSort;
    }

    public String getTotalamountSort() {
        return totalamountSort;
    }

    public void setTotalamountSort(String totalamountSort) {
        this.totalamountSort = totalamountSort;
    }

    public String getAgentTotalSaleSort() {
        return agentTotalSaleSort;
    }

    public void setAgentTotalSaleSort(String agentTotalSaleSort) {
        this.agentTotalSaleSort = agentTotalSaleSort;
    }
}
