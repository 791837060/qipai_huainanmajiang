package com.anbang.qipai.dalianmeng.cqrs.c.domain;

/**
 * 加入联盟结果
 */
public class JoinLianmengResult {
    private String powerAccountId;//能量账户
//    private String boxAccountId;//保险柜账户

    public String getPowerAccountId() {
        return powerAccountId;
    }

    public void setPowerAccountId(String powerAccountId) {
        this.powerAccountId = powerAccountId;
    }

//    public String getBoxAccountId() {
//        return boxAccountId;
//    }
//
//    public void setBoxAccountId(String boxAccountId) {
//        this.boxAccountId = boxAccountId;
//    }
}
