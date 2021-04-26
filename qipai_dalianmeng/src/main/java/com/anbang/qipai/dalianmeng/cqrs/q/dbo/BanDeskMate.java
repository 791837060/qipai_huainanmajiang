package com.anbang.qipai.dalianmeng.cqrs.q.dbo;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/11/14 9:21
 */
public class BanDeskMate {
    private String id;//id
    private String memberAId;
    private String memberBId;
    private String hehuorenId;
    private String lianmengId;
    private String operateId;
    private boolean suoyouxiaji=false;
    private long createTime;


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

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }


    public boolean isSuoyouxiaji() {
        return suoyouxiaji;
    }

    public void setSuoyouxiaji(boolean suoyouxiaji) {
        this.suoyouxiaji = suoyouxiaji;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getHehuorenId() {
        return hehuorenId;
    }

    public void setHehuorenId(String hehuorenId) {
        this.hehuorenId = hehuorenId;
    }
}
