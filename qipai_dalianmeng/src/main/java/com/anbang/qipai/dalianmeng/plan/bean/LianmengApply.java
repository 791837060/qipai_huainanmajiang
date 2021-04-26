package com.anbang.qipai.dalianmeng.plan.bean;

/**
 * 盟主申请
 * 
 * @author lsc
 *
 */
public class LianmengApply {
	private String id;
	private String memberId;// 申请人
	private String nickname;// 申请人昵称
	private String headimgurl;// 头像url
	private String status;// 申请状态
	private long createTime;// 申请时间

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
