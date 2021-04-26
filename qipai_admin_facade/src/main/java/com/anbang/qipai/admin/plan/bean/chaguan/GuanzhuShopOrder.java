package com.anbang.qipai.admin.plan.bean.chaguan;

/**
 * 茶馆商城订单
 *
 * @author ethan
 */
public class GuanzhuShopOrder {
    private String id;// 商户订单流水号
    private String pay_type;// 支付方式，支付宝或微信
    private String transaction_id;// 订单号
    private String status;// 微信或支付宝状态码
    private String payerId;// 付款人id
    private String payerName;// 付款人昵称
    private String receiverId;// 收货人id
    private String receiverName;// 收货人名字
    private String productId;// 商品id
    private String productName;// 商品名称
    private double productPrice;// 商品单价
    private int number;// 数量
    private RewardType rewardType;// 奖励类型
    private double rewardNum;// 奖励数量
    private double totalamount;// 总价
    private String reqIP;// 终端ip
    private long createTime;// 订单创建时间
    private long deliveTime;// 发货时间

    private double agentTotalSale;  // 累计销售金额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public double getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(double rewardNum) {
        this.rewardNum = rewardNum;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public String getReqIP() {
        return reqIP;
    }

    public void setReqIP(String reqIP) {
        this.reqIP = reqIP;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getDeliveTime() {
        return deliveTime;
    }

    public void setDeliveTime(long deliveTime) {
        this.deliveTime = deliveTime;
    }

    public double getAgentTotalSale() {
        return agentTotalSale;
    }

    public void setAgentTotalSale(double agentTotalSale) {
        this.agentTotalSale = agentTotalSale;
    }

    @Override
    public String toString() {
        return "GuanzhuShopOrder{" +
                "id='" + id + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", status='" + status + '\'' +
                ", payerId='" + payerId + '\'' +
                ", payerName='" + payerName + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", number=" + number +
                ", rewardType=" + rewardType +
                ", rewardNum=" + rewardNum +
                ", totalamount=" + totalamount +
                ", reqIP='" + reqIP + '\'' +
                ", createTime=" + createTime +
                ", deliveTime=" + deliveTime +
                ", agentTotalSale=" + agentTotalSale +
                '}';
    }
}
