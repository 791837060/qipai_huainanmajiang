package com.anbang.qipai.admin.plan.bean.signin;

public class SignInPrizeExchangeLog {//签到奖品兑奖纪录
    private String id;
    //private SignInPrizeLog signInPrizeLog;
    private String prizeName;
    private String memberId;
    private String nickName;
    private String phone;
    private String deliveryName;
    private String address;
    private long rewardTime;//兑奖时间
    private String issue;//是否发放,0：未发放，1、已发放
    private long issueTime;//发放时间

    public SignInPrizeExchangeLog() {
    }


    /**
     * 兑换的积分数量
     */
    private int score;
    /**
     * 兑换的话费或红包数量
     */
    private int currency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(long rewardTime) {
        this.rewardTime = rewardTime;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public long getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(long issueTime) {
        this.issueTime = issueTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }
}
