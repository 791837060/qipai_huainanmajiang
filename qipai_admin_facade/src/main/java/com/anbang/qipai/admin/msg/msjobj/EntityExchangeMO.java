package com.anbang.qipai.admin.msg.msjobj;

/**
 * @Author: 吴硕涵
 * @Date: 2018/12/27 9:27 AM
 * @Version 1.0
 */
public class EntityExchangeMO {

    String raffleRecordId;
    String memberId;
    String nickName;
    String lotteryName;
    String lotteryType;
    String singleNum;
    String telephone;
    String address;
    Long exchangeTime;
    Long distributeTime;
    String behavior;
    String rest;
    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
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

    public String getRaffleRecordId() {
        return raffleRecordId;
    }

    public void setRaffleRecordId(String raffleRecordId) {
        this.raffleRecordId = raffleRecordId;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }
}


