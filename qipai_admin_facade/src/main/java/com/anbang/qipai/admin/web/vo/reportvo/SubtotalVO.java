package com.anbang.qipai.admin.web.vo.reportvo;

/**
 * 活跃用户小计(昨日活跃,过去七日活跃,过去三十日活跃)
 * @author YaphetS
 * @date 2018/12/6
 */
public class SubtotalVO {
    /**
     * 活跃用户数
     */
    private Integer activeUser;
    /**
     * 日均在线时长
     */
    private Long dayOnlineTime;

    public SubtotalVO() {
    }

    public SubtotalVO(Integer activeUser, Long dayOnlineTime) {
        this.activeUser = activeUser;
        this.dayOnlineTime = dayOnlineTime;
    }

    public Integer getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(Integer activeUser) {
        this.activeUser = activeUser;
    }

    public Long getDayOnlineTime() {
        return dayOnlineTime;
    }

    public void setDayOnlineTime(Long dayOnlineTime) {
        this.dayOnlineTime = dayOnlineTime;
    }
}
