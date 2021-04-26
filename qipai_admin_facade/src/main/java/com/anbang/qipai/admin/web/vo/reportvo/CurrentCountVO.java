package com.anbang.qipai.admin.web.vo.reportvo;

/**
 * 统计实时更新的数据
 * @author YaphetS
 * @date 2018/11/22
 */

public class CurrentCountVO {
    /**
     * 今日新增
     */
    private Integer addCountToday;
    /**
     * 在线人数
     */
    private Integer onlineCount;
    /**
     * 启动次数
     */
    private Integer powerCount;
    /**
     * 活跃用户
     */
    private Integer activeUserCount;

    public CurrentCountVO() {
    }

    public CurrentCountVO(Integer addCountToday, Integer onlineCount, Integer powerCount, Integer activeUserCount) {
        this.addCountToday = addCountToday;
        this.onlineCount = onlineCount;
        this.powerCount = powerCount;
        this.activeUserCount = activeUserCount;
    }

    public Integer getAddCountToday() {
        return addCountToday;
    }

    public void setAddCountToday(Integer addCountToday) {
        this.addCountToday = addCountToday;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Integer getPowerCount() {
        return powerCount;
    }

    public void setPowerCount(Integer powerCount) {
        this.powerCount = powerCount;
    }

    public Integer getActiveUserCount() {
        return activeUserCount;
    }

    public void setActiveUserCount(Integer activeUserCount) {
        this.activeUserCount = activeUserCount;
    }
}
