package com.anbang.qipai.admin.plan.bean.report;



/**
 * 明细表(按天划分,所有数据汇聚到一张表中)
 * @author YaphetS
 * @date 2018/11/26
 */
public class DetailedReport {
    private String id;

    /**
     * 创建时间(以天为精度的时间戳)
     */
    private Long createTime;
    /**
     * 新增用户数
     */
    private Integer addUserCount;
    /**
     * 用户总量
     */
    private Integer totalUserCount;

    /**
     * 同时在线
     */
    private Integer onlineCount;
    /**
     * 峰值时段
     */
    private Long maxOnlineTime;
    /**
     * 登陆用户数
     */
    private Integer loginUser;
    /**
     * 启动次数统计
     */
    private Integer powerCount;
    /**
     * 活跃用户数
     */
    private Integer activeUser;
    /**
     * 日均在线时长
     */
    private Long dayOnlineTime;

    public DetailedReport() {
    }

    public DetailedReport(Long createTime) {
        this.createTime = createTime;
    }

    public DetailedReport(Long createTime, Integer onlineCount, Long maxOnlineTime, Integer loginUser) {
        this.createTime = createTime;
        this.onlineCount = onlineCount;
        this.maxOnlineTime = maxOnlineTime;
        this.loginUser = loginUser;
    }

    public DetailedReport(Long createTime, Integer onlineCount, Long maxOnlineTime) {
        this.createTime = createTime;
        this.onlineCount = onlineCount;
        this.maxOnlineTime = maxOnlineTime;
    }

    public DetailedReport(Long createTime,Integer loginUser) {
        this.createTime = createTime;
        this.loginUser = loginUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getAddUserCount() {
        return addUserCount;
    }

    public void setAddUserCount(Integer addUserCount) {
        this.addUserCount = addUserCount;
    }

    public Integer getTotalUserCount() {
        return totalUserCount;
    }

    public void setTotalUserCount(Integer totalUserCount) {
        this.totalUserCount = totalUserCount;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Long getMaxOnlineTime() {
        return maxOnlineTime;
    }

    public void setMaxOnlineTime(Long maxOnlineTime) {
        this.maxOnlineTime = maxOnlineTime;
    }

    public Integer getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Integer loginUser) {
        this.loginUser = loginUser;
    }

    public Integer getPowerCount() {
        return powerCount;
    }

    public void setPowerCount(Integer powerCount) {
        this.powerCount = powerCount;
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
