package com.anbang.qipai.admin.plan.bean.agents;

import com.anbang.qipai.admin.exceptions.AnBangException;
import org.apache.commons.lang.StringUtils;

public class AgentClubCard {
    private String id;
    private String product;// 商品名称
    private String productPic;// ICON图
    private int number;
    private String payType;// 支付方式
    private double price;// 价格
    private int weight;// 权重
    private boolean sale;// 是否可用

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public void valid() throws AnBangException {
        if (StringUtils.isEmpty(product)
                || StringUtils.isEmpty(payType)) {
            throw new AnBangException("参数类型错误");
        }

        if (!valueOf(product)) {
            throw new AnBangException("非法的商品");
        }

    }

    private boolean valueOf(String product) {
        switch (product) {
            case "日卡":
                return true;
            case "周卡":
                return true;
            case "月卡":
                return true;
            case "季卡":
                return true;
            case "玉石":
                return true;
            default:
                return false;
        }
    }

}
