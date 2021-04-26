package com.anbang.qipai.dalianmeng.cqrs.q.dbo;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/11/14 17:40
 */
public class BanDeskmateRecord {
    private String id;
    private String memberAId;
    private String memberBId;
    private String operateId;
    private String lianmengId;
    private long createTime;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberAId() {
        return memberAId;
    }

    public void setMemberAId(String memberAId) {
        this.memberAId = memberAId;
    }

    public String getMemberBId() {
        return memberBId;
    }

    public void setMemberBId(String memberBId) {
        this.memberBId = memberBId;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
