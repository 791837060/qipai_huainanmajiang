package com.anbang.qipai.admin.msg.msjobj;

public class MemberRaffleHistoryMo {
    private String id;
    private String memberId;
    private LotteryMo lottery;
    private Address address;
    private long time;
    private boolean firstTime;


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

    public LotteryMo getLottery() {
        return lottery;
    }

    public void setLottery(LotteryMo lottery) {
        this.lottery = lottery;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
}
