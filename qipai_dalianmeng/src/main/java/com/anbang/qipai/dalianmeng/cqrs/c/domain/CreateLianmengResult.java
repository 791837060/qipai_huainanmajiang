package com.anbang.qipai.dalianmeng.cqrs.c.domain;

public class CreateLianmengResult {
    private String lianmengId;
    private String powerAccountId;  //能量账户
    private String boxAccountId;    //保险柜账户
    private String scoreAccountId;  //贡献分账户

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public String getPowerAccountId() {
        return powerAccountId;
    }

    public void setPowerAccountId(String powerAccountId) {
        this.powerAccountId = powerAccountId;
    }

    public String getBoxAccountId() {
        return boxAccountId;
    }

    public void setBoxAccountId(String boxAccountId) {
        this.boxAccountId = boxAccountId;
    }

    public String getScoreAccountId() {
        return scoreAccountId;
    }

    public void setScoreAccountId(String scoreAccountId) {
        this.scoreAccountId = scoreAccountId;
    }
}
