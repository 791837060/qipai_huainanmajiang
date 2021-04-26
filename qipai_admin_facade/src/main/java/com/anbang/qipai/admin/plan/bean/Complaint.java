package com.anbang.qipai.admin.plan.bean;

public class Complaint {
    private String memberId;
    private String nickname;
    private String complaintText;//投诉内容
    private long complaintTime;//投诉时间

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public long getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(long complaintTime) {
        this.complaintTime = complaintTime;
    }
}
