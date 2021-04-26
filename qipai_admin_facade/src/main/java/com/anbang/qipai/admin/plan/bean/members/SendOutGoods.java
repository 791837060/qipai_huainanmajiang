package com.anbang.qipai.admin.plan.bean.members;

/**
 * @Author: 吴硕涵
 * @Date: 2018/12/27 1:28 PM
 * @Version 1.0
 */
public class SendOutGoods {
    String memberId;
    String raffleRecordId;
    String hasSent;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRaffleRecordId() {
        return raffleRecordId;
    }

    public void setRaffleRecordId(String raffleRecordId) {
        this.raffleRecordId = raffleRecordId;
    }

    public String getHasSent() {
        return hasSent;
    }

    public void setHasSent(String hasSent) {
        this.hasSent = hasSent;
    }
}
