package com.anbang.qipai.admin.msg.msjobj;

import com.anbang.qipai.admin.remote.LotteryMoTypeEnum;

public class LotteryMo {
    private String id;
    private String name;
    private int prop;
    private int firstPop;
    private LotteryMoTypeEnum type;
    private int singleNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProp() {
        return prop;
    }

    public void setProp(int prop) {
        this.prop = prop;
    }

    public int getFirstPop() {
        return firstPop;
    }

    public void setFirstPop(int firstPop) {
        this.firstPop = firstPop;
    }



    public int getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(int singleNum) {
        this.singleNum = singleNum;
    }

    public LotteryMoTypeEnum getType() {
        return type;
    }

    public void setType(LotteryMoTypeEnum type) {
        this.type = type;
    }
}
