package com.anbang.qipai.admin.web.vo.reportvo;

/**
 * 在线人数明细的VO
 * @author YaphetS
 * @date 2018/11/23
 */
public class OnlineCountVO {
    private Long date;
    /**
     * 同时在线
     */
    private Integer onlineCount;
    /**
     * 峰值时段
     */
    private Integer maxOnlineTime;
    /**
     * 登陆用户数
     */
    private Integer loginUser;

    public OnlineCountVO() {
    }

    public OnlineCountVO(Long date, Integer onlineCount, Integer maxOnlineTime, Integer loginUser) {
        this.date = date;
        this.onlineCount = onlineCount;
        this.maxOnlineTime = maxOnlineTime;
        this.loginUser = loginUser;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Integer getMaxOnlineTime() {
        return maxOnlineTime;
    }

    public void setMaxOnlineTime(Integer maxOnlineTime) {
        this.maxOnlineTime = maxOnlineTime;
    }

    public Integer getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Integer loginUser) {
        this.loginUser = loginUser;
    }
}
