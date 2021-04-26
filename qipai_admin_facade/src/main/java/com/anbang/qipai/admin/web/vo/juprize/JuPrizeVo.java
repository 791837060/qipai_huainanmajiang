package com.anbang.qipai.admin.web.vo.juprize;

import com.anbang.qipai.admin.plan.bean.juprize.DrawTypeEnum;
import com.anbang.qipai.admin.plan.bean.juprize.JuPrize;

/**
 * @Description:
 */
public class JuPrizeVo {
    private String id;
    private String name;
    private String prizeType;//奖品类型
    private int singleNum;//单奖数量
    private int storeNum;//库存数量
    private String iconUrl;
    private int prizeProb;//中奖概率
    private boolean overstep;//超出奖池

    private DrawTypeEnum drawType;  // 抽奖行为本身的类型

    public JuPrizeVo(JuPrize juPrize) {
        this.id = juPrize.getId();
        this.name = juPrize.getName();
        switch (juPrize.getPrizeType()) {
            case HONGBAODIAN:
                this.prizeType = "红包点";
                break;
            default:juPrize.getPrizeType().name();

        }
        this.singleNum = juPrize.getSingleNum();
        this.storeNum = juPrize.getStoreNum();
        this.iconUrl = juPrize.getIconUrl();
        this.prizeProb = juPrize.getPrizeProb();
        this.overstep = juPrize.isOverstep();
        this.drawType = juPrize.getDrawType();
    }

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

    public String getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(String prizeType) {
        this.prizeType = prizeType;
    }

    public int getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(int singleNum) {
        this.singleNum = singleNum;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getPrizeProb() {
        return prizeProb;
    }

    public void setPrizeProb(int prizeProb) {
        this.prizeProb = prizeProb;
    }

    public boolean isOverstep() {
        return overstep;
    }

    public void setOverstep(boolean overstep) {
        this.overstep = overstep;
    }

    public DrawTypeEnum getDrawType() {
        return drawType;
    }

    public void setDrawType(DrawTypeEnum drawType) {
        this.drawType = drawType;
    }
}
