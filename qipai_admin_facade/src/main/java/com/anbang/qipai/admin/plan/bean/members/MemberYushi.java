package com.anbang.qipai.admin.plan.bean.members;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/12/9 10:39
 */
public class MemberYushi {
    private String id;// 玉石id
    private String name;// 玉石名称
    private double price;// 玉石价格
    private double firstDiscount;// 首次折扣
    private double firstDiscountPrice;// 首次折扣后价格
    private int yushi;// 玉石数量

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFirstDiscount() {
        return firstDiscount;
    }

    public void setFirstDiscount(double firstDiscount) {
        this.firstDiscount = firstDiscount;
    }

    public double getFirstDiscountPrice() {
        return firstDiscountPrice;
    }

    public void setFirstDiscountPrice(double firstDiscountPrice) {
        this.firstDiscountPrice = firstDiscountPrice;
    }

    public int getYushi() {
        return yushi;
    }

    public void setYushi(int yushi) {
        this.yushi = yushi;
    }
}
