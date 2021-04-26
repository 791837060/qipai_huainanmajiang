package com.anbang.qipai.admin.plan.bean.report;


/**
 * @author YaphetS
 * @date 2018/11/30
 */
public class GameRecord {
    private String id;

    /**
     * 最后一次记录时间
     */
    private Long createTime;

    /**
     * 用户id
     */
    private String memberId;

    /**
     * 日均在线时长
     */
    private Long DayOnlineTime;

    public GameRecord() {
    }

    public GameRecord(Long createTime, String memberId, Long dayOnlineTime) {
        this.createTime = createTime;
        this.memberId = memberId;
        DayOnlineTime = dayOnlineTime;
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

    public Long getDayOnlineTime() {
        return DayOnlineTime;
    }

    public void setDayOnlineTime(Long dayOnlineTime) {
        DayOnlineTime = dayOnlineTime;
    }
}
