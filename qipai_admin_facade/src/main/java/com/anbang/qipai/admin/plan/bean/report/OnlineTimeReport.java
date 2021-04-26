package com.anbang.qipai.admin.plan.bean.report;

/**
 * @author YaphetS
 * @date 2018/12/5
 */
public class OnlineTimeReport {

    private String id;
    /**
     * 创建时间与用户id的组合唯一
     * 创建时间
     */
    private Long createTime;
    /**
     * 用户id
     */
    private String memberId;
    /**
     * 在线时长
     */
    private Long onlineTime;

    public OnlineTimeReport() {
    }

    public OnlineTimeReport(Long createTime, String memberId, Long onlineTime) {
        this.createTime = createTime;
        this.memberId = memberId;
        this.onlineTime = onlineTime;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Long onlineTime) {
        this.onlineTime = onlineTime;
    }
}
