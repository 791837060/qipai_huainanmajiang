package com.anbang.qipai.dalianmeng.cqrs.q.dbo;

/**
 * 成员申请记录
 */
public class MemberApplyingRecord {
    private String id;
    private String lianmengId;//联盟
    private Identity identity;//身份
    private String memberId;//申请人
    private String nickname;//申请人昵称
    private String headimgurl;//申请人头像
    private String state;//申请状态
    private long createTime;//申请时间
    private String inviteMemberId;//上级的id
    private Identity inviterIdentity;//上级身份
    private String inviteMemberNickname;//上级昵称
    private String inviteMemberHeadimgurl;//上级头像
    private String auditorMemberId;//审核人用户Id
    private String auditorMemberNickname;//审核人昵称
    private String auditorMemberHeadimgurl;//审核人头像

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getInviteMemberId() {
        return inviteMemberId;
    }

    public void setInviteMemberId(String inviteMemberId) {
        this.inviteMemberId = inviteMemberId;
    }


    public String getInviteMemberNickname() {
        return inviteMemberNickname;
    }

    public void setInviteMemberNickname(String inviteMemberNickname) {
        this.inviteMemberNickname = inviteMemberNickname;
    }

    public String getInviteMemberHeadimgurl() {
        return inviteMemberHeadimgurl;
    }

    public void setInviteMemberHeadimgurl(String inviteMemberHeadimgurl) {
        this.inviteMemberHeadimgurl = inviteMemberHeadimgurl;
    }

    public String getAuditorMemberId() {
        return auditorMemberId;
    }

    public void setAuditorMemberId(String auditorMemberId) {
        this.auditorMemberId = auditorMemberId;
    }

    public String getAuditorMemberNickname() {
        return auditorMemberNickname;
    }

    public void setAuditorMemberNickname(String auditorMemberNickname) {
        this.auditorMemberNickname = auditorMemberNickname;
    }

    public String getAuditorMemberHeadimgurl() {
        return auditorMemberHeadimgurl;
    }

    public void setAuditorMemberHeadimgurl(String auditorMemberHeadimgurl) {
        this.auditorMemberHeadimgurl = auditorMemberHeadimgurl;
    }
}
