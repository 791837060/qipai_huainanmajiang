package com.anbang.qipai.admin.plan.bean.report;



/**
 * 每小时在线人数表
 * @author YaphetS
 * @date 2018/11/23
 */
public class BasicDataReport {

    private String id;

    /**
     * 时间(20180101 0点)
     */
    private Long createTime;

    /**
     * 当前在线(90)
     */
    private Integer currentQuantity;

    /**
     * 最高在线(100)
     */
    private Integer maxQuantity;

    public BasicDataReport() {
    }

    public BasicDataReport(Long createTime, Integer currentQuantity, Integer maxQuantity) {
        this.createTime = createTime;
        this.currentQuantity = currentQuantity;
        this.maxQuantity = maxQuantity;
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

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }
}
