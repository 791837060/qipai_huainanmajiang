package com.anbang.qipai.admin.web.query;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class MemberQuery {

    private String id;
    private String nickname;
    private String onlineState;
    private List<String> ids;

    private String goldSort;
    private String scoreSort;
    private String createTimeSort;
    private String vipLevelSort;
    private String vipScoreSort;
    private String onlineStateSort;
    private String costSort;
    private String goldTotalCostSort;
    private String agentId;

    public Sort getSort() {
        List<Sort.Order> orderList = new ArrayList<>();
        if ("ASC".equals(goldSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "gold"));
        } else if ("DESC".equals(goldSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "gold"));
        }
        if ("ASC".equals(scoreSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "score"));
        } else if ("DESC".equals(scoreSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "score"));
        }
        if ("ASC".equals(createTimeSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "createTime"));
        } else if ("DESC".equals(createTimeSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "createTime"));
        }
        if ("ASC".equals(vipLevelSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "vipLevel"));
        } else if ("DESC".equals(vipLevelSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "vipLevel"));
        }
        if ("ASC".equals(vipScoreSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "vipScore"));
        } else if ("DESC".equals(vipScoreSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "vipScore"));
        }
        if ("ASC".equals(onlineStateSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "onlineState"));
        } else if ("DESC".equals(onlineStateSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "onlineState"));
        }
        if ("ASC".equals(costSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "cost"));
        } else if ("DESC".equals(costSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "cost"));
        }
        if ("ASC".equals(goldTotalCostSort)) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, "goldTotalCost"));
        } else if ("DESC".equals(goldTotalCostSort)) {
            orderList.add(new Sort.Order(Sort.Direction.DESC, "goldTotalCost"));
        }
        if (!orderList.isEmpty()) {
            Sort sort = new Sort(orderList);
            return sort;
        }
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
        return sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getGoldSort() {
        return goldSort;
    }

    public void setGoldSort(String goldSort) {
        this.goldSort = goldSort;
    }

    public String getScoreSort() {
        return scoreSort;
    }

    public void setScoreSort(String scoreSort) {
        this.scoreSort = scoreSort;
    }

    public String getCreateTimeSort() {
        return createTimeSort;
    }

    public void setCreateTimeSort(String createTimeSort) {
        this.createTimeSort = createTimeSort;
    }

    public String getVipLevelSort() {
        return vipLevelSort;
    }

    public void setVipLevelSort(String vipLevelSort) {
        this.vipLevelSort = vipLevelSort;
    }

    public String getVipScoreSort() {
        return vipScoreSort;
    }

    public void setVipScoreSort(String vipScoreSort) {
        this.vipScoreSort = vipScoreSort;
    }

    public String getOnlineStateSort() {
        return onlineStateSort;
    }

    public void setOnlineStateSort(String onlineStateSort) {
        this.onlineStateSort = onlineStateSort;
    }

    public String getCostSort() {
        return costSort;
    }

    public void setCostSort(String costSort) {
        this.costSort = costSort;
    }

    public String getGoldTotalCostSort() {
        return goldTotalCostSort;
    }

    public void setGoldTotalCostSort(String goldTotalCostSort) {
        this.goldTotalCostSort = goldTotalCostSort;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
