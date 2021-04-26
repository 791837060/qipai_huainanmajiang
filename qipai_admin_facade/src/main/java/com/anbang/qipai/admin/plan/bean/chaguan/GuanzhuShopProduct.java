package com.anbang.qipai.admin.plan.bean.chaguan;

/**
 * 馆主商城商品
 *
 * @author ethan
 */
public class GuanzhuShopProduct {
    private String id;
    private String agentId;  // 代理id
    private String agentName;  //代理名称
    private String name; //商品名称
    private String productPic;// ICON图
    private RewardType rewardType;// 商品类型
    private int rewardNum;// 商品数量
    private double price;   // 商品价格

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public int getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(int rewardNum) {
        this.rewardNum = rewardNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GuanzhuShopProduct{" +
                "id='" + id + '\'' +
                ", agentId='" + agentId + '\'' +
                ", agentName='" + agentName + '\'' +
                ", name='" + name + '\'' +
                ", productPic='" + productPic + '\'' +
                ", rewardType=" + rewardType +
                ", rewardNum=" + rewardNum +
                ", price=" + price +
                '}';
    }
}
