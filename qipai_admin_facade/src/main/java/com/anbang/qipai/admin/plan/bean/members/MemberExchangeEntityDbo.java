package com.anbang.qipai.admin.plan.bean.members;

/**
 * @Author: 吴硕涵
 * @Date: 2018/12/25 2:48 PM
 * @Version 1.0
 */
public class MemberExchangeEntityDbo {
    private String id;
    private String raffleRecordId;
    private String memberId;
    private String nickName;
    private String lotteryName;
    private String lotteryId;
    private String lotteryType;
    private String singleNum;
    private String telephone;
    private String address;
    private Long exchangeTime;
    private Long distributeTime;
    private String behavior;
    private Boolean hasExchange;
    private String realName;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(String singleNum) {
        this.singleNum = singleNum;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Long exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public Long getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Long distributeTime) {
        this.distributeTime = distributeTime;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public Boolean getHasExchange() {
        return hasExchange;
    }

    public void setHasExchange(Boolean hasExchange) {
        this.hasExchange = hasExchange;
    }

    public String getRaffleRecordId() {
        return raffleRecordId;
    }

    public void setRaffleRecordId(String raffleRecordId) {
        this.raffleRecordId = raffleRecordId;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
